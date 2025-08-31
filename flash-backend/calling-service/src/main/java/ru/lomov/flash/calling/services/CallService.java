package ru.lomov.flash.calling.services;

import ru.lomov.flash.calling.dto.CallDto;
import ru.lomov.flash.calling.dto.CallRequestDto;

import java.util.List;
import java.util.Optional;

public interface CallService {

    CallDto initiateCall(CallRequestDto callRequest);

    Optional<CallDto> getCallById(String callId);

    List<CallDto> getCallsForUser(String userId);

    CallDto updateCallStatus(String callId, String status);

    void endCall(String callId, String endReason);

    void deleteCall(String callId);
}
