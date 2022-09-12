package game.yachu.controller;

import game.yachu.controller.request.RecordRequest;
import game.yachu.repository.RecordRepository;
import game.yachu.repository.dto.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class RecordController {

    private final RecordRepository recordRepository;

    public RecordController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @ResponseBody
    @GetMapping("/api/record")
    public List<Record> findTop10() {
        return recordRepository.findTop10();
    }

    @ResponseBody
    @PostMapping("/api/record/new")
    public Long save(@RequestBody RecordRequest request) {
        Record record = new Record(request.getNickname(), request.getScore());
        recordRepository.save(record); // record id 삽입
        log.info("Record Save - id={}", record.getId());
        return record.getId();
    }
}
