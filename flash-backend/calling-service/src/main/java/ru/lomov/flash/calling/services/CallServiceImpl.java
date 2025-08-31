package ru.lomov.flash.calling.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flash.calling.dto.CallDto;
import ru.lomov.flash.calling.dto.CallRequestDto;
import ru.lomov.flash.calling.entities.Call;
import ru.lomov.flash.calling.repositories.CallRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CallServiceImpl implements CallService {

    private final CallRepository callRepository;

    @Override
    public CallDto initiateCall(CallRequestDto callRequest) {
        Call call = new Call();
        call.setId(UUID.randomUUID().toString());
        call.setCallerId(callRequest.getCallerId());
        call.setReceiverId(callRequest.getReceiverId());
        call.setType(Call.CallType.valueOf(callRequest.getType().name()));
        call.setStatus(Call.CallStatus.INITIATED);
        call.setRoomId(generateRoomId());

        Call savedCall = callRepository.save(call);
        return mapToDto(savedCall);
    }

    @Override
    public Optional<CallDto> getCallById(String callId) {
        return callRepository.findById(callId).map(this::mapToDto);
    }

    @Override
    public List<CallDto> getCallsForUser(String userId) {
        return callRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CallDto updateCallStatus(String callId, String status) {
        Optional<Call> callOptional = callRepository.findById(callId);
        if (callOptional.isPresent()) {
            Call call = callOptional.get();
            call.setStatus(Call.CallStatus.valueOf(status));

            if ("CONNECTED".equals(status) && call.getStartedAt() == null) {
                call.setStartedAt(LocalDateTime.now());
            } else if ("ENDED".equals(status) && call.getEndedAt() == null) {
                call.setEndedAt(LocalDateTime.now());
                if (call.getStartedAt() != null) {
                    call.setDuration(java.time.Duration.between(call.getStartedAt(), call.getEndedAt()).getSeconds());
                }
            }

            Call updatedCall = callRepository.save(call);
            return mapToDto(updatedCall);
        }
        throw new RuntimeException("Call not found with id: " + callId);
    }

    @Override
    public void endCall(String callId, String endReason) {
        Optional<Call> callOptional = callRepository.findById(callId);
        if (callOptional.isPresent()) {
            Call call = callOptional.get();
            call.setStatus(Call.CallStatus.ENDED);
            call.setEndReason(endReason);
            call.setEndedAt(LocalDateTime.now());

            if (call.getStartedAt() != null) {
                call.setDuration(java.time.Duration.between(call.getStartedAt(), call.getEndedAt()).getSeconds());
            }

            callRepository.save(call);
        }
    }

    @Override
    public void deleteCall(String callId) {
        callRepository.deleteById(callId);
    }

    private String generateRoomId() {
        return "room_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private CallDto mapToDto(Call call) {
        CallDto dto = new CallDto();
        dto.setId(call.getId());
        dto.setCallerId(call.getCallerId());
        dto.setReceiverId(call.getReceiverId());
        dto.setType(CallDto.CallType.valueOf(call.getType().name()));
        dto.setStatus(CallDto.CallStatus.valueOf(call.getStatus().name()));
        dto.setRoomId(call.getRoomId());
        dto.setStartedAt(call.getStartedAt());
        dto.setEndedAt(call.getEndedAt());
        dto.setDuration(call.getDuration());
        dto.setEndReason(call.getEndReason());
        dto.setCreatedAt(call.getCreatedAt());
        dto.setUpdatedAt(call.getUpdatedAt());
        return dto;
    }
}
