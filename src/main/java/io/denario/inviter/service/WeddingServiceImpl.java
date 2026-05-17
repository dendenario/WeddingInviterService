package io.denario.inviter.service;


import io.denario.inviter.controller.web.model.WeddingDetailsDto;
import io.denario.inviter.data.repository.WeddingDetailsEntity;
import io.denario.inviter.data.repository.WeddingDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public WeddingDetailsDto getWeddingDetails() {
        WeddingDetailsEntity entity = repository.getDetails();
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public void updateWeddingDetails(WeddingDetailsDto dto) {
        WeddingDetailsEntity current = repository.getDetails();

        WeddingDetailsEntity updatedEntity = mapper.toEntity(dto);
        if (current != null) {
            // Гарантируем, что мы обновляем существующую запись конфигурации, а не создаем новую
            updatedEntity.setId(current.getId());
        }
        repository.save(updatedEntity);
    }
}
