package com.android.autelsdk.util

import android.content.ContentValues
import android.content.Context
import android.nfc.Tag
import android.os.Environment
import android.util.Log
import android.widget.Toast
import org.apache.poi.hssf.record.formula.functions.T
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ExcelWorkbook {

    private lateinit var cell: Cell
    private lateinit var sheet: Sheet

    private val EXCEL_SHEET_NAME = "Sheet1"
    var workbook: Workbook = HSSFWorkbook()

    /**
     * Method: Generate Excel Workbook
     */
      fun createExcelWorkbook() {
        // New Workbook

        // Cell style for header row
        val cellStyle: CellStyle = workbook.createCellStyle()
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index)
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER)

        // New Sheet
        sheet = workbook.createSheet(EXCEL_SHEET_NAME)

        // Generate column headings
        val row: Row = sheet.createRow(0)
        cell = row.createCell(0)
        cell.setCellValue("Sl.No")
        cell.setCellStyle(cellStyle)
        cell = row.createCell(1)
        cell.setCellValue("check Name")
        cell.setCellStyle(cellStyle)
        cell = row.createCell(2)
        cell.setCellValue("status")
        cell.setCellStyle(cellStyle)
        cell = row.createCell(3)
        cell.setCellValue("Activity Name")
        cell.setCellStyle(cellStyle)
    }


    fun readExcelFromStorage(context : Context, fileName:String) {
        var file  =  File(context.getExternalFilesDir(null), fileName);
        var fileInputStream : FileInputStream? = null ;

        try {
            fileInputStream =  FileInputStream(file);
            Log.e(ContentValues.TAG, "Reading from Excel" + file);

            // Create instance having reference to .xls file
            workbook =  HSSFWorkbook(fileInputStream);

            // Fetch sheet at position 'i' from the workbook
            sheet = workbook.getSheetAt(0);

            // Iterate through each row
            for (row : Row in sheet) {
                if (row.getRowNum() > 0) {
                    // Iterate through all the cells in a row (Excluding header row)
                    val cellIterator : Iterator<Cell> = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        var cell : Cell = cellIterator.next();

                        // Check cell type and format accordingly
                        when(cell.getCellType()) {

                            Cell.CELL_TYPE_NUMERIC ->
                                // Print cell value
                                System.out.println(cell.getNumericCellValue());

                            Cell.CELL_TYPE_STRING ->
                                System.out.println(cell.getStringCellValue());

                        }
                    }
                }
            }
        }catch ( e : Exception) {
            Log.e(ContentValues.TAG, "Error Reading Exception: ", e);
        } catch ( e:Exception) {
            Log.e(ContentValues.TAG, "Failed to read file due to Exception: ", e);
        } finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (ex : Exception ) {
                ex.printStackTrace();
            }
        }
    }

    fun exportDataIntoWorkbook(
        context: Context
    ): Boolean {
        val isWorkbookWrittenIntoStorage: Boolean

        // Check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(ContentValues.TAG, "Storage not available or read only")
            return false
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = HSSFWorkbook()
        //setHeaderCellStyle()

        // Creating a New Sheet and Setting width for each column
        sheet = workbook.createSheet(EXCEL_SHEET_NAME)
        sheet.setColumnWidth(0, 15 * 400)
        sheet.setColumnWidth(1, 15 * 400)
        sheet.setColumnWidth(2, 15 * 400)
        sheet.setColumnWidth(3, 15 * 400)
        setHeaderRow()
        //fillDataIntoExcel(dataList)
        isWorkbookWrittenIntoStorage = storeExcelInStorage(context,"TestFolder")
        return isWorkbookWrittenIntoStorage
    }

    /**
     * Checks if Storage is READ-ONLY
     *
     * @return boolean
     */
    private fun isExternalStorageReadOnly(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == externalStorageState
    }

    /**
     * Checks if Storage is Available
     *
     * @return boolean
     */
    private fun isExternalStorageAvailable(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == externalStorageState
    }

    /**
     * Setup header cell style
     */
//    private fun setHeaderCellStyle() {
//        headerCellStyle = workbook.createCellStyle()
//        headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index)
//        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
//        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER)
//    }

    /**
     * Setup Header Row
     */
    private fun setHeaderRow() {
        val cellStyle: CellStyle = workbook.createCellStyle()
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index)
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER)
        val headerRow = sheet.createRow(0)
        cell = headerRow.createCell(0)
        cell.setCellValue("First Name")
        cell.cellStyle = cellStyle
        cell = headerRow.createCell(1)
        cell.setCellValue("Last Name")
        cell.cellStyle = cellStyle
        cell = headerRow.createCell(2)
        cell.setCellValue("Phone Number")
        cell.cellStyle = cellStyle
        cell = headerRow.createCell(3)
        cell.setCellValue("Mail ID")
        cell.cellStyle = cellStyle
    }

    /**
     * Fills Data into Excel Sheet
     *
     *
     * NOTE: Set row index as i+1 since 0th index belongs to header row
     *
     * @param dataList - List containing data to be filled into excel
     */
    private fun fillDataIntoExcel(dataList: List<String>) {
        for (i in dataList.indices) {
            // Create a New Row for every new entry in list
            val rowData = sheet.createRow(i + 1)

            // Create Cells for each row
            cell = rowData.createCell(0)
            cell.setCellValue(dataList[i].getFirstName())
            cell = rowData.createCell(1)
            cell.setCellValue(dataList[i].getLastName())
            cell = rowData.createCell(2)
            cell.setCellValue(dataList[i].getPhoneNumber())
            cell = rowData.createCell(4)
            cell.setCellValue(dataList[i].getMailId())
        }
    }

    private fun fillDataIntoExcel(dataList: List<String>, col : Int) {
        for (i in dataList.indices) {
            // Create a New Row for every new entry in list
            val rowData = sheet.createRow(i + 1)

            // Create Cells for each row
            cell = rowData.createCell(col)
            cell.setCellValue(dataList[i])

        }
    }

    /**
     * Store Excel Workbook in external storage
     *
     * @param context  - application context
     * @param fileName - name of workbook which will be stored in device
     * @return boolean - returns state whether workbook is written into storage or not
     */
    public fun storeExcelInStorage(context: Context, fileName: String): Boolean {
        Log.i("KLKLKL","called")
        var isSuccess: Boolean
       // val file = File(Environment.DIRECTORY_DOWNLOADS, fileName)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName + ".xls"
        )
        var fileOutputStream: FileOutputStream? = null
        try {
            Log.i("KLKLKL","try called")

            if (!file.exists()) {
                file.createNewFile()
            }
            fileOutputStream = FileOutputStream(file)
            workbook.write(fileOutputStream)
            Log.e(ContentValues.TAG, "Writing file$file")
            isSuccess = true
            Log.i("KLKLKL","saved")
        } catch (e: IOException) {
            Log.i("KLKLKL","IO"+e.message)

            Log.e(ContentValues.TAG, "Error writing Exception: ", e)
            isSuccess = false
        } catch (e: java.lang.Exception) {
            Log.i("KLKLKL","error")

            Log.e(ContentValues.TAG, "Failed to save file due to Exception: ", e)
            isSuccess = false
        } finally {
            Log.i("KLKLKL","final")

            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close()
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
        return isSuccess
    }


}