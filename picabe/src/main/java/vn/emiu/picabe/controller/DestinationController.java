package vn.emiu.picabe.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.emiu.picabe.entity.Destination;
import vn.emiu.picabe.service.DestinationService;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }
}
