package com.android.autelsdk.flyController

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.autelsdk.util.Resource
import com.autel.common.CallbackWithNoParam
import com.autel.common.CallbackWithOneParam
import com.autel.common.error.AutelError
import com.autel.common.flycontroller.*
import com.qxwz.sdk.core.Constants
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


//import com.google.common.collect.Table.Cell




class FlyControllerRepositoryImpl<AutelFlyController>(
    private val mController: com.autel.sdk.flycontroller.AutelFlyController,
) : FlyControllerRepository{

    protected var ledPilotLamp = LedPilotLamp.ALL_OFF

    override fun setBeginnerModeStateTest(enable : Boolean): MutableLiveData<Resource<String>> {
        var setBeginnerModeStateTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setBeginnerModeEnable(enable, object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setBeginnerModeStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setBeginnerModeStateTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setBeginnerModeStateTestResult
    }

    override fun getBeginnerModeStateTest(): MutableLiveData<Resource<Boolean>> {
        var getBeginnerModeStateTestResult : MutableLiveData<Resource<Boolean>> = MutableLiveData()
        mController.isBeginnerModeEnable( object : CallbackWithOneParam<Boolean> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getBeginnerModeStateTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(mode: Boolean?) {
                getBeginnerModeStateTestResult.postValue(Resource.Companion.success(mode))
            }
        })
        return getBeginnerModeStateTestResult
    }



    override fun getMaxHeightTest(): MutableLiveData<Resource<Float>> {
        var getMaxHeightTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getMaxHeight( object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(value: Float?) {
                val successMessage = "";
                getMaxHeightTestResult.postValue(Resource.Companion.success(value))
            }
        })
        return getMaxHeightTestResult
    }

    override fun setMaxHeightTest(): MutableLiveData<Resource<String>> {
        var setMaxHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxHeight( value,object : CallbackWithNoParam {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setMaxHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setMaxHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxHeightTestResult
    }

    override fun getMaxRangeTest(): MutableLiveData<Resource<Float>> {
        var getMaxRangeTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()
        mController.getMaxRange( object : CallbackWithOneParam<Float> {
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(maxRange : Float?) {
                val successMessage = "";
                getMaxRangeTestResult.postValue(Resource.Companion.success(maxRange))
            }
        })
        return getMaxRangeTestResult
    }

    override fun setMaxRangeTest(): MutableLiveData<Resource<String>> {
        var setMaxRangeTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxRange( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setMaxRangeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setMaxRangeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setMaxRangeTestResult
    }

    override fun getReturnHeightTest(): MutableLiveData<Resource<Float>> {
        var getReturnHeightTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()

        mController.getReturnHeight(object : CallbackWithOneParam<Float>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(returnHeight : Float?) {
                val successMessage = "";
                getReturnHeightTestResult.postValue(Resource.Companion.success(returnHeight))
            }
        })
        return getReturnHeightTestResult
    }

    override fun setReturnHeightTest(): MutableLiveData<Resource<String>> {
        var setReturnHeightTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setReturnHeight( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setReturnHeightTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setReturnHeightTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setReturnHeightTestResult
    }

    override fun getHorizontalSpeedTest(): MutableLiveData<Resource<Float>> {
        var getHorizontalSpeedTestResult : MutableLiveData<Resource<Float>> = MutableLiveData()

        mController.getMaxHorizontalSpeed(object : CallbackWithOneParam<Float>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getHorizontalSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(horizontalSpeed : Float?) {
                val successMessage = "";
                getHorizontalSpeedTestResult.postValue(Resource.Companion.success(horizontalSpeed))
            }
        })
        return getHorizontalSpeedTestResult
    }

    override fun setHorizontalSpeedTest(): MutableLiveData<Resource<String>> {
        var setHorizontalSpeedTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setMaxHorizontalSpeed( value,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setHorizontalSpeedTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setHorizontalSpeedTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setHorizontalSpeedTestResult
    }

    override fun isAttiModeEnableTest(): MutableLiveData<Resource<Boolean>> {
        var isAttiModeEnableTestResult : MutableLiveData<Resource<Boolean>> = MutableLiveData()

        mController.isAttitudeModeEnable(object : CallbackWithOneParam<Boolean>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                isAttiModeEnableTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(result : Boolean?) {
                val successMessage = "";
                isAttiModeEnableTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return isAttiModeEnableTestResult
    }

    override fun setAttiModeEnableTest(enable: Boolean): MutableLiveData<Resource<Boolean>> {
        var setAttiModeEnableTestResult : MutableLiveData<Resource<Boolean>> = MutableLiveData()
        mController.setAttitudeModeEnable( enable,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setAttiModeEnableTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setAttiModeEnableTestResult.postValue(Resource.Companion.success(enable))
            }
        })
        return setAttiModeEnableTestResult
    }

    override fun getLedPilotLampTest(): MutableLiveData<Resource<LedPilotLamp>> {
        var getLedPilotLampTestResult : MutableLiveData<Resource<LedPilotLamp>> = MutableLiveData()

        mController.getLedPilotLamp(object : CallbackWithOneParam<LedPilotLamp>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getLedPilotLampTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(ledpilotlamp : LedPilotLamp?) {
                val successMessage = "";
                getLedPilotLampTestResult.postValue(Resource.Companion.success(ledpilotlamp))
            }
        })
        return getLedPilotLampTestResult
    }

    override fun setLedPilotLampTest(): MutableLiveData<Resource<String>> {
        var setLedPilotLampTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.setLedPilotLamp( ledPilotLamp,object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setLedPilotLampTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setLedPilotLampTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLedPilotLampTestResult
    }

    override fun setLocationAsHomePointTest(): MutableLiveData<Resource<String>> {
        val lat = 22.0
        val lon = 22.0

        var setLocationAsHomePointTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setLocationAsHomePoint(lat,lon, object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setLocationAsHomePointTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setLocationAsHomePointTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setLocationAsHomePointTestResult
    }

    override fun setAircraftLocationAsHomePointTest(): MutableLiveData<Resource<String>> {

        var setAircraftLocationAsHomePointTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.setAircraftLocationAsHomePoint( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setAircraftLocationAsHomePointTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                setAircraftLocationAsHomePointTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return setAircraftLocationAsHomePointTestResult
    }

    override fun startCalibrateCompassTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
        val lat = 22.0
        val lon = 22.0

        var startCalibrateCompassTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
        var value : Double
        value = 200.0
        mController.startCalibrateCompass( object : CallbackWithOneParam<CalibrateCompassStatus>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                startCalibrateCompassTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(calibrateCompassStatus : CalibrateCompassStatus) {
                val successMessage = "";
                startCalibrateCompassTestResult.postValue(Resource.Companion.success(calibrateCompassStatus))
            }
        })
        return startCalibrateCompassTestResult
    }

    /**override fun takeOffTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
    val lat = 22.0
    val lon = 22.0

    var takeOffTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
    var value : Double
    value = 200.0
    mController.takeOff( object : CallbackWithOneParam<Pair<Boolean>>{
    override fun onFailure(rcError: AutelError) {
    val errorMessage = "";
    takeOffTestResult.postValue(Resource.Companion.error(errorMessage, null))
    }

    override fun onSuccess(calibrateCompassStatus : CalibrateCompassStatus) {
    val successMessage = "";
    takeOffTestResult.postValue(Resource.Companion.success(calibrateCompassStatus))
    }
    })
    return takeOffTestResult
    }*/

    override fun landTest(): MutableLiveData<Resource<String>> {

        var landTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.land( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                landTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                landTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return landTestResult
    }

    override fun goHome(): MutableLiveData<Resource<String>> {

        var goHomeTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.goHome( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                goHomeTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                goHomeTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return goHomeTestResult
    }

    override fun cancelReturnTest(): MutableLiveData<Resource<String>> {

        var cancelReturnTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.cancelReturn( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                cancelReturnTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                cancelReturnTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return cancelReturnTestResult
    }

    override fun cancelLandTest(): MutableLiveData<Resource<String>> {

        var cancelLandTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.cancelLand( object : CallbackWithNoParam{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                cancelLandTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess() {
                val successMessage = "";
                cancelLandTestResult.postValue(Resource.Companion.success(successMessage))
            }
        })
        return cancelLandTestResult
    }

    override fun getVersionInfoTest(): MutableLiveData<Resource<FlyControllerVersionInfo>> {

        var getVersionInfoTestResult : MutableLiveData<Resource<FlyControllerVersionInfo>> = MutableLiveData()
        mController.getVersionInfo( object : CallbackWithOneParam<FlyControllerVersionInfo>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getVersionInfoTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(flyresult : FlyControllerVersionInfo) {
                val successMessage = "";
                getVersionInfoTestResult.postValue(Resource.Companion.success(flyresult))
            }
        })
        return getVersionInfoTestResult
    }

    override fun getSerialNumberTest(): MutableLiveData<Resource<String>> {

        var getSerialNumberTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
        mController.getSerialNumber( object : CallbackWithOneParam<String>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                getSerialNumberTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(result : String) {
                val successMessage = "";
                getSerialNumberTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return getSerialNumberTestResult
    }

    override fun setCalibrateCompassListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {


        var setCalibrateCompassListenerTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
        mController.setCalibrateCompassListener( object : CallbackWithOneParam<CalibrateCompassStatus>{
            override fun onFailure(rcError: AutelError) {
                val errorMessage = "";
                setCalibrateCompassListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
            }

            override fun onSuccess(result : CalibrateCompassStatus) {
                val successMessage = "";
                setCalibrateCompassListenerTestResult.postValue(Resource.Companion.success(result))
            }
        })
        return setCalibrateCompassListenerTestResult
    }

    /**override fun setWarningListenerTest(): MutableLiveData<Resource<CalibrateCompassStatus>> {
    val lat = 22.0
    val lon = 22.0

    var setWarningListenerTestResult : MutableLiveData<Resource<CalibrateCompassStatus>> = MutableLiveData()
    var value : Double
    value = 200.0
    mController.setWarningListener( object : CallbackWithOneParam<ARMWarning, MagnetometerState>,
    CallbackWithTwoParams<ARMWarning, MagnetometerState> {
    override fun onFailure(rcError: AutelError) {
    val errorMessage = "";
    setWarningListenerTestResult.postValue(Resource.Companion.error(errorMessage, null))
    }

    override fun onSuccess(ARMWarning data1, MagnetometerState data2) {
    val successMessage = "";
    setWarningListenerTestResult.postValue(Resource.Companion.success(data2+data1))
    }
    })
    return setWarningListenerTestResult
    }*/


    private lateinit var cell: Cell
    private lateinit var sheet: Sheet

    private val EXCEL_SHEET_NAME = "Sheet1"
    var workbook: Workbook = HSSFWorkbook()

    /**
     * Method: Generate Excel Workbook
     */
    override  fun createExcelWorkbook() {
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


     fun readExcelFromStorage( context : Context,  fileName:String) {
        var file  =  File(context.getExternalFilesDir(null), fileName);
         var fileInputStream : FileInputStream? = null ;

        try {
            fileInputStream =  FileInputStream(file);
            Log.e(TAG, "Reading from Excel" + file);

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
                        var cell : Cell= cellIterator.next();

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
            Log.e(TAG, "Error Reading Exception: ", e);
        } catch ( e:Exception) {
            Log.e(TAG, "Failed to read file due to Exception: ", e);
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
        context: Context, fileName: String,
        dataList: List<String>
    ): Boolean {
        val isWorkbookWrittenIntoStorage: Boolean

        // Check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only")
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
        fillDataIntoExcel(dataList)
        isWorkbookWrittenIntoStorage = storeExcelInStorage(context, fileName)
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
        val headerRow = sheet.createRow(0)
        cell = headerRow.createCell(0)
        cell.setCellValue("First Name")
        cell.cellStyle
        cell = headerRow.createCell(1)
        cell.setCellValue("Last Name")
        cell.cellStyle
        cell = headerRow.createCell(2)
        cell.setCellValue("Phone Number")
        cell.cellStyle
        cell = headerRow.createCell(3)
        cell.setCellValue("Mail ID")
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
//            cell = rowData.createCell(0)
//            cell.setCellValue(dataList[i].getFirstName())
//            cell = rowData.createCell(1)
//            cell.setCellValue(dataList[i].getLastName())
//            cell = rowData.createCell(2)
//            cell.setCellValue(dataList[i].getPhoneNumber())
//            cell = rowData.createCell(4)
//            cell.setCellValue(dataList[i].getMailId())
        }
    }

    /**
     * Store Excel Workbook in external storage
     *
     * @param context  - application context
     * @param fileName - name of workbook which will be stored in device
     * @return boolean - returns state whether workbook is written into storage or not
     */
    private fun storeExcelInStorage(context: Context, fileName: String): Boolean {
        var isSuccess: Boolean
        val file = File(context.getExternalFilesDir(null), fileName)
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            workbook.write(fileOutputStream)
            Log.e(TAG, "Writing file$file")
            isSuccess = true
        } catch (e: IOException) {
            Log.e(TAG, "Error writing Exception: ", e)
            isSuccess = false
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Failed to save file due to Exception: ", e)
            isSuccess = false
        } finally {
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









//    override fun setLanguageTest(language : RemoteControllerLanguage): MutableLiveData<Resource<String>> {
//        var setLanguageTestResult : MutableLiveData<Resource<String>> = MutableLiveData()
//        mController.setLanguage(language, object : CallbackWithNoParam {
//            override fun onFailure(rcError: AutelError) {
//                val errorMessage = "";
//                setLanguageTestResult.postValue(Resource.Companion.error(errorMessage, null))
//            }
//
//            override fun onSuccess() {
//                val successMessage = "";
//                setLanguageTestResult.postValue(Resource.Companion.success(successMessage))
//            }
//        })
//        return setLanguageTestResult
//    }


}