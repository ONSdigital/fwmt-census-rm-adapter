package uk.gov.ons.fwmt.census.rmadapter.message.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.gov.ons.fwmt.census.rmadapter.data.CensusCaseOutcomeDTO;
import uk.gov.ons.fwmt.census.rmadapter.message.impl.JobServiceReceiver;
import uk.gov.ons.fwmt.census.rmadapter.service.RMAdapterService;
import uk.gov.ons.fwmt.fwmtgatewaycommon.data.DummyTMResponse;
import uk.gov.ons.fwmt.fwmtgatewaycommon.error.CTPException;
import uk.gov.ons.fwmt.fwmtohsjobstatusnotification.FwmtOHSJobStatusNotification;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceReceiverImplTest {

  @InjectMocks
  private JobServiceReceiver jobServiceReceiver;

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private RMAdapterService rmAdapterService;

  @Test
  public void receiveMessage() throws IOException, CTPException {
    //Given
    String testReturnXML = "returnXML";
    CensusCaseOutcomeDTO censusCaseOutcomeDTO = new CensusCaseOutcomeDTO();
    when(objectMapper.readValue(eq(testReturnXML), eq(CensusCaseOutcomeDTO.class))).thenReturn(censusCaseOutcomeDTO);

    //When
    jobServiceReceiver.receiveMessage(testReturnXML);

    //Then
    verify(rmAdapterService).returnJobRequest(censusCaseOutcomeDTO);
  }

  @Test(expected = CTPException.class)
  public void receiveMessageBadJson() throws IOException, CTPException {
    //Given
    String testReturnXML = "returnXML";
    when(objectMapper.readValue(eq(testReturnXML), eq(CensusCaseOutcomeDTO.class))).thenThrow(new IOException());

    //When
    jobServiceReceiver.receiveMessage(testReturnXML);

    fail("Exception was not thrown");
  }
}