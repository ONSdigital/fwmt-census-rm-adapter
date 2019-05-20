package uk.gov.ons.census.fwmt.rmadapter.helper;

import uk.gov.ons.ctp.response.action.message.instruction.ActionAddress;
import uk.gov.ons.ctp.response.action.message.instruction.ActionCancel;
import uk.gov.ons.ctp.response.action.message.instruction.ActionContact;
import uk.gov.ons.ctp.response.action.message.instruction.ActionInstruction;
import uk.gov.ons.ctp.response.action.message.instruction.ActionPause;
import uk.gov.ons.ctp.response.action.message.instruction.ActionRequest;
import uk.gov.ons.ctp.response.action.message.instruction.ActionUpdate;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static java.lang.Boolean.TRUE;
import static uk.gov.ons.census.fwmt.rmadapter.utils.UtilityMethods.getXMLGregorianCalendarNow;

public class ActionInstructionBuilder {

  public ActionInstruction createActionInstructionBuilder() throws DatatypeConfigurationException {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionRequest actionRequest = new ActionRequest();
    ActionAddress actionAddress = new ActionAddress();

    actionRequest.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionRequest.setSurveyRef("testSurveyRef");
    actionRequest.setReturnByDate("11/11/2000");
    actionRequest.setUndeliveredAsAddress(false);
    actionRequest.setBlankQreReturned(false);

    ActionContact contact = new ActionContact();

    actionAddress.setLatitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLongitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLine1("addressLine1");
    actionAddress.setLine2("addressLine2");
    actionAddress.setPostcode("testPostcode");
    actionAddress.setTownName("testTownName");

    actionRequest.setAddress(actionAddress);
    actionInstruction.setActionRequest(actionRequest);
    actionRequest.setContact(contact);

    actionRequest.setAddressType("HH");

    return actionInstruction;
  }

  public ActionInstruction createActionInstructionBuilderCEE() throws DatatypeConfigurationException {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionRequest actionRequest = new ActionRequest();
    ActionAddress actionAddress = new ActionAddress();

    actionRequest.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionRequest.setSurveyRef("testSurveyRef");
    actionRequest.setReturnByDate("11/11/2000");
    actionRequest.setUndeliveredAsAddress(false);
    actionRequest.setBlankQreReturned(false);

    ActionContact contact = new ActionContact();

    actionAddress.setLatitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLongitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLine1("addressLine1");
    actionAddress.setLine2("addressLine2");
    actionAddress.setPostcode("testPostcode");
    actionAddress.setTownName("testTownName");

    actionRequest.setAddress(actionAddress);
    actionInstruction.setActionRequest(actionRequest);
    actionRequest.setContact(contact);

    actionRequest.setAddressType("CE");
    actionRequest.setAddressLevel("E");
    actionRequest.setCeDeliveryReqd(true);
    actionRequest.setCeCE1Complete(false);
    actionRequest.setCeExpectedResponses(BigInteger.valueOf(20));
    actionRequest.setCeActualResponses(BigInteger.valueOf(15));

    return actionInstruction;
  }

  public ActionInstruction createActionInstructionBuilderCEU() throws DatatypeConfigurationException {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionRequest actionRequest = new ActionRequest();
    ActionAddress actionAddress = new ActionAddress();

    actionRequest.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionRequest.setSurveyRef("testSurveyRef");
    actionRequest.setReturnByDate("11/11/2000");
    actionRequest.setUndeliveredAsAddress(false);
    actionRequest.setBlankQreReturned(false);

    ActionContact contact = new ActionContact();

    actionAddress.setLatitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLongitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLine1("addressLine1");
    actionAddress.setLine2("addressLine2");
    actionAddress.setPostcode("testPostcode");
    actionAddress.setTownName("testTownName");

    actionRequest.setAddress(actionAddress);
    actionInstruction.setActionRequest(actionRequest);
    actionRequest.setContact(contact);

    actionRequest.setAddressType("CE");
    actionRequest.setAddressLevel("U");
    actionRequest.setCeDeliveryReqd(true);
    actionRequest.setCeCE1Complete(false);
    actionRequest.setCeExpectedResponses(BigInteger.valueOf(20));
    actionRequest.setCeActualResponses(BigInteger.valueOf(15));

    return actionInstruction;
  }

  public ActionInstruction cancelActionInstructionBuilder() {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionCancel actionCancel = new ActionCancel();

    actionCancel.setReason("reason");
    actionCancel.setActionId("testActionID");
    actionCancel.setCaseRef("testCaseRef");
    actionCancel.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionCancel.setAddressType("HH");

    actionInstruction.setActionCancel(actionCancel);

    return actionInstruction;
  }

  public ActionInstruction updateActionInstructionBuilder() {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionUpdate actionUpdate = new ActionUpdate();

    actionInstruction.setActionUpdate(actionUpdate);

    return actionInstruction;
  }

  public ActionInstruction createNisraActionInstructionBuilder () throws DatatypeConfigurationException {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionRequest actionRequest = new ActionRequest();
    ActionAddress actionAddress = new ActionAddress();

    actionRequest.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionRequest.setSurveyRef("testSurveyRef");
    actionRequest.setReturnByDate("11/11/2000");
    actionRequest.setUndeliveredAsAddress(false);
    actionRequest.setBlankQreReturned(false);

    ActionContact contact = new ActionContact();

    actionAddress.setLatitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLongitude(BigDecimal.valueOf(1000.00));
    actionAddress.setLine1("addressLine1");
    actionAddress.setLine2("addressLine2");
    actionAddress.setPostcode("testPostcode");
    actionAddress.setTownName("testTownName");

    actionRequest.setAddress(actionAddress);
    actionInstruction.setActionRequest(actionRequest);
    actionRequest.setContact(contact);
    actionRequest.setFieldOfficerId("testFieldOfficer");

    actionRequest.setAddressType("HH");
    ActionPause actionPause = new ActionPause();
    actionPause.setCode("code");
    actionPause.setEffectiveDate(getXMLGregorianCalendarNow());
    actionPause.setHoldUntil(getXMLGregorianCalendarNow());
    actionPause.setReason("reason");

    actionRequest.setPause(actionPause);

    return actionInstruction;
  }

  public ActionInstruction cancelActionInstructionBuilderForPause() {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionCancel actionCancel = new ActionCancel();

    actionCancel.setReason("reason");
    actionCancel.setActionId("testActionID");
    actionCancel.setCaseRef("testCaseRef");
    actionCancel.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionCancel.setAddressType("HH");

    actionInstruction.setActionCancel(actionCancel);

    return actionInstruction;
  }

  public ActionInstruction cancelActionInstructionBuilderForNonHouseHold() {
    ActionInstruction actionInstruction = new ActionInstruction();
    ActionCancel actionCancel = new ActionCancel();

    actionCancel.setReason("reason");
    actionCancel.setActionId("testActionID");
    actionCancel.setCaseRef("testCaseRef");
    actionCancel.setCaseId("8ed3fc08-e95f-44db-a6d7-cde4e76a6182");
    actionCancel.setAddressType("CC");

    actionInstruction.setActionCancel(actionCancel);

    return actionInstruction;
  }
}
