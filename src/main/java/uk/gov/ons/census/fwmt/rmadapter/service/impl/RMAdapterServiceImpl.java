package uk.gov.ons.census.fwmt.rmadapter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import uk.gov.ons.census.fwmt.common.error.GatewayException;
import uk.gov.ons.census.fwmt.events.component.GatewayEventManager;
import uk.gov.ons.census.fwmt.rmadapter.canonical.CanonicalJobHelper;
import uk.gov.ons.census.fwmt.rmadapter.message.GatewayActionProducer;
import uk.gov.ons.census.fwmt.rmadapter.redis.HouseholdStore;
import uk.gov.ons.census.fwmt.rmadapter.service.RMAdapterService;
import uk.gov.ons.ctp.response.action.message.instruction.ActionInstruction;

@Slf4j
@Component
public class RMAdapterServiceImpl implements RMAdapterService {

  @Autowired
  private GatewayEventManager gatewayEventManager;

  @Autowired
  private GatewayActionProducer jobServiceProducer;

  @Autowired
  private HouseholdStore householdStore;

  private boolean caseIdIsPresent = false;

  public void sendJobRequest(ActionInstruction actionInstruction) throws GatewayException {
    if (actionInstruction.getActionRequest() != null) {
      sendCreateMessage(actionInstruction);
      householdStore.cacheJob(actionInstruction.getActionRequest().getCaseId());
      jobServiceProducer.sendMessage(CanonicalJobHelper.newCreateJob(actionInstruction));
      gatewayEventManager
          .triggerEvent(actionInstruction.getActionRequest().getCaseId(), CANONICAL_CREATE_SENT, LocalTime.now());
    } else if (actionInstruction.getActionCancel() != null) {
      createCancelMessage(actionInstruction);
    } else if (actionInstruction.getActionUpdate() != null) {
      createUpdateMessage(actionInstruction);
    } else {
      String msg = "No matching request was found";
      gatewayEventManager.triggerErrorEvent(this.getClass(), msg, actionInstruction.getActionRequest().getCaseId(), INVALID_ACTION_INSTRUCTION);
      throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, msg);
    }
  }

  private void createUpdateMessage(ActionInstruction actionInstruction) throws GatewayException {
    jobServiceProducer.sendMessage(CanonicalJobHelper.newUpdateJob(actionInstruction));
    gatewayEventManager.triggerEvent(actionInstruction.getActionUpdate().getCaseId(), CANONICAL_UPDATE_SENT);
  }

  private void createCancelMessage(ActionInstruction actionInstruction) throws GatewayException {
    if (actionInstruction.getActionCancel().getAddressType().equals("HH")) {
      jobServiceProducer.sendMessage(CanonicalJobHelper.newCancelJob(actionInstruction));
      gatewayEventManager.triggerEvent(actionInstruction.getActionCancel().getCaseId(), CANONICAL_CANCEL_SENT);
    } else {
      throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, "Valid address type not found");
    }
  }

  private void sendCreateMessage(ActionInstruction actionInstruction) throws GatewayException {
    jobServiceProducer.sendMessage(CanonicalJobHelper.newCreateJob(actionInstruction));
    gatewayEventManager.triggerEvent(actionInstruction.getActionRequest().getCaseId(), CANONICAL_CREATE_SENT);
      if (!StringUtils.isEmpty(householdStore.retrieveCache(actionInstruction.getActionCancel().getCaseId()))) {
        if (actionInstruction.getActionCancel().getAddressType().equals("HH")) {
          jobServiceProducer.sendMessage(CanonicalJobHelper.newCancelJob(actionInstruction));
          gatewayEventManager
                  .triggerEvent(actionInstruction.getActionCancel().getCaseId(), CANONICAL_CANCEL_SENT, LocalTime.now());
        } else {
          throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, "Valid address type not found");
        }
      } else {
        throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, "No matching case ID was found for " +
                actionInstruction.getActionCancel().getCaseId() + " when a Action Cancel Request was tempted.");
      }
    } else if (actionInstruction.getActionUpdate() != null) {
      if (!StringUtils.isEmpty(householdStore.retrieveCache(actionInstruction.getActionUpdate().getCaseId()))) {
        if (actionInstruction.getActionUpdate().getAddressType().equals("HH")) {
          jobServiceProducer.sendMessage(CanonicalJobHelper.newUpdateJob(actionInstruction));
          gatewayEventManager
                  .triggerEvent(actionInstruction.getActionUpdate().getCaseId(), CANONICAL_UPDATE_SENT, LocalTime.now());
        } else {
          throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, "Valid address type not found");
        }
      } else {
        throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, "No matching case ID was found for " +
                actionInstruction.getActionCancel().getCaseId() + " when a Action Update Request was tempted.");
      }
    } else {
      throw new GatewayException(GatewayException.Fault.SYSTEM_ERROR, "No matching request was found");
    }
  }
}
