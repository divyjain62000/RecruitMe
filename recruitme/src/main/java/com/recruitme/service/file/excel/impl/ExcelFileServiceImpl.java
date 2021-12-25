package com.recruitme.service.file.excel.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.recruitme.service.domain.student.registration.drive.dto.StudentRegisteredInDriveResponseDTO;
import com.recruitme.service.file.excel.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ExcelFileServiceImpl implements ExcelFileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * To export excel file
     * @param header
     * @param data
     * @param columns
     * @param fileName
     * @return {@link ByteArrayInputStream}
     */
    @Override
    public ByteArrayInputStream exportStudentRegisteredInDriveExcelFile(List<String> header, List<StudentRegisteredInDriveResponseDTO> data,
                                                               int columns,String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(fileName);

            CellStyle headerCellStyle = this.getHeaderCellStyle(workbook);

            // Creating header cells
            sheet = createHeader(sheet, headerCellStyle, header);

            CellStyle cellStyle=this.getCellStyle(workbook);

            // Creating data rows for each contact
            int dataSize = data.size();
            for (int i = 0; i < dataSize; i++) {
                Row dataRow = sheet.createRow(i + 1);
                Cell cell1=dataRow.createCell(0);
                cell1.setCellValue(data.get(i).getStudentName());
                cell1.setCellStyle(cellStyle);

                Cell cell2=dataRow.createCell(1);
                cell2.setCellValue(data.get(i).getPrimaryEmail());
                cell2.setCellStyle(cellStyle);

                Cell cell3=dataRow.createCell(2);
                cell3.setCellValue(data.get(i).getSecondaryEmail());
                cell3.setCellStyle(cellStyle);

                Cell cell4=dataRow.createCell(3);
                cell4.setCellValue(data.get(i).getPrimaryMobileNumber());
                cell4.setCellStyle(cellStyle);

                Cell cell5=dataRow.createCell(4);
                cell5.setCellValue(data.get(i).getSecondaryMobileNumber());
                cell5.setCellStyle(cellStyle);

                Cell cell6=dataRow.createCell(5);
                cell6.setCellValue(data.get(i).getBranch());
                cell6.setCellStyle(cellStyle);

                Cell cell7=dataRow.createCell(6);
                cell7.setCellValue(data.get(i).getProgram());
                cell7.setCellStyle(cellStyle);

                Cell cell8=dataRow.createCell(7);
                cell8.setCellValue(data.get(i).getSeniorSecondaryMarks());
                cell8.setCellStyle(cellStyle);

                Cell cell9=dataRow.createCell(8);
                cell9.setCellValue(data.get(i).getHighSchoolMarks());
                cell9.setCellStyle(cellStyle);

                Cell cell10=dataRow.createCell(9);
                cell10.setCellValue(data.get(i).getUgMarks());
                cell10.setCellStyle(cellStyle);

                if(data.get(i).getPgMarks()!=null) {
                    Cell cell11=dataRow.createCell(10);
                    cell11.setCellValue(data.get(i).getPgMarks());
                    cell11.setCellStyle(cellStyle);
                }
            }

        // Making size of column auto resize to fit with data

            for (int i = 0; i < columns; i++) {
                sheet.autoSizeColumn(i,true);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            logger.error("Error during export Excel file", ex);
            return null;
        }
    }

    /**
     * It return header cell style
     * @param workbook
     * @return {@link CellStyle}
     */
    private CellStyle getHeaderCellStyle(Workbook workbook) {
        // Define header cell style
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return headerCellStyle;
    }

    /**
     * Get cell style
     * @param workbook
     * @return {@link CellStyle}
     */
    private CellStyle getCellStyle(Workbook workbook) {
        // Define header cell style
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return cellStyle;
    }

    /**
     * To create header of excel file
     * @param sheet
     * @param headerCellStyle
     * @param header
     * @return {@link Sheet}
     */
    private Sheet createHeader(Sheet sheet, CellStyle headerCellStyle, List<String> header) {
        Row row = sheet.createRow(0);
        int headerSize = header.size();
        for (int i = 0; i < headerSize; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header.get(i));
            cell.setCellStyle(headerCellStyle);
        }
        return sheet;
    }

}