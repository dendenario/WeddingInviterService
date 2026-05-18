package io.denario.inviter.service;


import io.denario.inviter.controller.web.model.WeddingDetailsDto;
import io.denario.inviter.data.repository.WeddingDetailsEntity;
import io.denario.inviter.data.repository.WeddingDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WeddingServiceImpl implements WeddingService {

    private final WeddingDetailsRepository repository;
    private final WeddingMapper mapper;

    public WeddingServiceImpl(WeddingDetailsRepository repository, WeddingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WeddingDetailsDto> getWeddingDetails() {
        return repository.getDetails().map(mapper::toDto);
    }

    @Override
    @Transactional
    public void updateWeddingDetails(WeddingDetailsDto dto) {
        WeddingDetailsEntity updatedEntity = mapper.toEntity(dto);
        repository.getDetails()
                .ifPresent(current -> updatedEntity.setId(current.getId()));
        repository.save(updatedEntity);
    }
}
