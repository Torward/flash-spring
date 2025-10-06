package ru.lomov.flashbackend.reportproductservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.reportproductservice.dto.CreateProductReportDto;
import ru.lomov.flashbackend.reportproductservice.dto.ProductReportDto;
import ru.lomov.flashbackend.reportproductservice.entity.ProductReport;
import ru.lomov.flashbackend.reportproductservice.repository.ProductReportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReportServiceImpl implements ProductReportService {

    private final ProductReportRepository productReportRepository;

    @Override
    public ProductReportDto createReport(CreateProductReportDto createProductReportDto) {
        if (productReportRepository.existsByProductIdAndReportedByUserId(
                createProductReportDto.getProductId(),
                createProductReportDto.getReportedByUserId())) {
            throw new IllegalArgumentException("User has already reported this product");
        }

        ProductReport productReport = new ProductReport();
        productReport.setProductId(createProductReportDto.getProductId());
        productReport.setReportedByUserId(createProductReportDto.getReportedByUserId());
        productReport.setReason(createProductReportDto.getReason());
        productReport.setResolved(false);

        ProductReport savedReport = productReportRepository.save(productReport);
        return mapToDto(savedReport);
    }

    @Override
    public ProductReportDto getReportById(String reportId) {
        ProductReport productReport = productReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Product report not found with id: " + reportId));
        return mapToDto(productReport);
    }

    @Override
    public List<ProductReportDto> getReportsByProductId(String productId) {
        return productReportRepository.findByProductId(productId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReportDto> getReportsByUserId(String userId) {
        return productReportRepository.findByReportedByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReportDto> getAllReports() {
        return productReportRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductReportDto mapToDto(ProductReport productReport) {
        return ProductReportDto.builder()
                .reportId(productReport.getReportId())
                .productId(productReport.getProductId())
                .reportedByUserId(productReport.getReportedByUserId())
                .reason(productReport.getReason())
                .resolved(productReport.isResolved())
                .createdAt(productReport.getCreatedAt())
                .build();
    }
}