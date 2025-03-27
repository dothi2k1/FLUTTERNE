package vn.emiu.picabe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.emiu.picabe.entity.Destination;
import vn.emiu.picabe.repository.DestinationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }
}
