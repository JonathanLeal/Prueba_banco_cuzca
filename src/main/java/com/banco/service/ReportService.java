package com.banco.service;

import com.banco.dto.SpaceOccupancyReportDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<SpaceOccupancyReportDTO> getOccupancyReport(LocalDateTime start, LocalDateTime end);
}