package com.s0qva.application.service;

import com.s0qva.application.dto.IncomeStatementDto;
import com.s0qva.application.dto.SoldCommodityDto;
import com.s0qva.application.mapper.DefaultMapper;
import com.s0qva.application.model.IncomeStatement;
import com.s0qva.application.repository.IncomeStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeStatementService {
    private final MailService mailService;
    private final PdfService pdfService;
    private final SoldCommodityService soldCommodityService;
    private final UserService userService;
    private final IncomeStatementRepository incomeStatementRepository;

    @Transactional
    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    public void sendIncomeStatementReport() {
        commitIncomeStatement();

        var timeReport = LocalDate.now();
        var pdfFileName = "income-statement-report-" + timeReport + ".pdf";
        var pdfFilePath = "report/incomestatement/" + pdfFileName;
        var admins = userService.getAllAdmins();

        pdfService.createPdf(pdfFilePath, getLatest());

        for (var admin : admins) {
            mailService.sendEmail(
                    admin.getEmail(),
                    "OnlineShop: Отчет о прибыли за " + timeReport,
                    EMPTY,
                    pdfFileName,
                    pdfFilePath
            );
        }
    }

    public IncomeStatementDto getLatest() {
        return incomeStatementRepository.findLatest()
                .map(incomeStatement -> DefaultMapper.mapToDto(incomeStatement, IncomeStatementDto.class))
                .orElseThrow();
    }

    private void commitIncomeStatement() {
        var soldCommodities = soldCommodityService.getAllDuringCurrentDay();
        var incomeStatement = IncomeStatement.builder()
                .amountOfSoldCommodities(soldCommodities.size())
                .incomeAmount(getIncomeAmount(soldCommodities))
                .reportDate(System.currentTimeMillis())
                .build();
        incomeStatementRepository.save(incomeStatement);
    }

    private Double getIncomeAmount(List<SoldCommodityDto> soldCommodities) {
        return soldCommodities.stream()
                .reduce(0.0, (subCost, currentSoldCommodityDto) -> {
                    var currentCommodityDto = currentSoldCommodityDto.getCommodity();
                    var currentAmountOfSoldCommodities = currentSoldCommodityDto.getAmountOfSoldCommodities();

                    return subCost + currentCommodityDto.getCost() * currentAmountOfSoldCommodities;
                }, Double::sum);
    }
}
