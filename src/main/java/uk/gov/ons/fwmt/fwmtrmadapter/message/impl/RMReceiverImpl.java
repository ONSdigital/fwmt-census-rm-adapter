package uk.gov.ons.fwmt.fwmtrmadapter.message.impl;

import lombok.extern.slf4j.Slf4j;
import uk.gov.ons.fwmt.fwmtrmadapter.message.RMReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.ons.fwmt.fwmtrmadapter.service.RMAdapterService;
import uk.gov.ons.ctp.response.action.message.instruction.ActionInstruction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

@Component
@Slf4j
public class RMReceiverImpl implements RMReceiver {

  @Autowired
  private RMAdapterService rmAdapterService;

  public void receiveMessage(byte[] createJobRequestXML) throws JAXBException {

    log.info(new String(createJobRequestXML));
    JAXBContext jaxbContext = JAXBContext.newInstance(ActionInstruction.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    ByteArrayInputStream input = new ByteArrayInputStream(createJobRequestXML);
    JAXBElement<ActionInstruction> rmActionInstruction = unmarshaller.unmarshal(new StreamSource(input), ActionInstruction.class);
    rmAdapterService.sendJobRequest(rmActionInstruction.getValue());

  }
}

