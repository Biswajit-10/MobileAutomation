package key;

import java.awt.Desktop;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

import org.aspectj.weaver.loadtime.Agent;
import org.omg.CORBA.Environment;
import org.openqa.selenium.remote.BrowserType;

import com.google.common.base.Stopwatch;

import bsh.Console;
import okio.Options;

public class Keyword {
    
    private Desktop silkTest = Agent.Desktop;
    
    private ProcessStartInfo psi = new ProcessStartInfo();
    
    private ProcessStartInfo psiFile = new ProcessStartInfo();
    
    private string path = "C:\\SBI_SilkTest\\SBI_ARTP";
    
    private TestObject objValue;
    
    private TestObject recoveryobj;
    
    private Stopwatch watch = new Stopwatch();
    
    private double retrieved_Data_1;
    
    private double retrieved_Data_2;
    
    private DataSet oobjectMapExtractor;
    
    private string retrieveValueExtractorctor;
    
    private boolean ConditionalFlag = false;
    
    private boolean strBatchMode = false;
    
    private int TestCaseExecutionIntervalTime = 3;
    
    private int maxWaitInSeconds;
    
    public static void Main(IDictionary<string, object> args) {
        strBatchMode = boolean.Parse(args("BatchMode"));
        Main.Main();
    }
    
    public static void Main() {
        Agent.SetOption("OPT_XBROWSER_ENABLE_JAVASCRIPT_INJECTION", false);
        Agent.SetOption("OPT_XBROWSER_TIMER_SYNC_MODE", 0);
        string strInpFile;
        if ((strBatchMode == true)) {
            strInpFile = (path + "\\Input_File\\Batch_Run");
            TestCaseExecutionIntervalTime = 8;
        }
        else {
            strInpFile = (path + "\\Input_File");
            TestCaseExecutionIntervalTime = 3;
        }
        
        System.IO.DirectoryInfo Inputpath = new System.IO.DirectoryInfo(strInpFile);
        for (System.IO.FileInfo file : Inputpath.GetFiles) {
            try {
                inputFilePath = file.FullName;
                file_name = file.Name.Split(".");
                scenarioname = file_name(lbound(file_name));
                if ((file_name(Ubound(file_name)) == "csv")) {
                    Main.initConfigurations();
                    Main.createOutputFolder(scenarioname);
                    DebugLogFilePath = (outputDatePath + "\\DebugLog.txt");
                    DebugLogFSO = createobject("Scripting.FileSystemObject");
                    if (!DebugLogFSO.FileExists(DebugLogFilePath)) {
                        DebugLogFile = DebugLogFSO.CreateTextFile(DebugLogFilePath);
                        DebugLogFile.close();
                        DebugLogFile = null;
                    }
                    
                    if ((DebugLogFile == null)) {
                        DebugLogFile = DebugLogFSO.OpenTextFile(DebugLogFilePath, 8);
                        WriteConsoleLog("Created DebugLogFSO object");
                    }
                    else {
                        WriteConsoleLog("Reusing the same DebugLogFSO object");
                    }
                    
                    //                 WriteConsoleLog("initConfigurations and createOutputFolder finished")
                    //                 MsgBox(DebugLogFilePath)
                    //                 Call DeleteDLPTemp()'Delete Temp IE Files
                    WriteConsoleLog(("******************************** ARTP " 
                                    + (scenarioname + (" execution Started BatchMode = " 
                                    + (strBatchMode + "********************************")))));
                    WriteConsoleLog(("Input File path : " + strInpFile));
                    resultFlag = "Pass";
                    recoveryFlag = 0;
                    objFSO = createobject("Scripting.FileSystemObject");
                    objFile = objFSO.OpenTextFile(inputFilePath);
                    objOutputFile = objFSO.OpenTextFile(outputFilePath, 2);
                    if (((resultFlag == "Pass") 
                                || (resultFlag == "Recover"))) {
                        // ConditionalFlag = False
                        while (!objFile.AtEndOfStream) {
                            if (((resultFlag == "Terminated") 
                                        || (resultFlag == "Fail"))) {
                                if (((resultFlag == "Terminated") 
                                            && ((ConditionalFlag == false) 
                                            && (InputBox(("Script : " 
                                                + (scenarioID + (" Current Step No : " 
                                                + (stepID + " . Continue Yes or No"))))).ToUpper() == "YES")))) {
                                    resultFlag = "Pass";
                                }
                                else {
                                    break;
                                }
                                
                            }
                            
                            actualMessage = "Same as Expected";
                            if ((resultFlag == "Pass")) {
                                fileData = objFile.ReadLine;
                                // Read next line only when the result is pass else if recover then continue with the same
                            }
                            
                            arrData = fileData.Split(",");
                            // msgbox(fileData)
                            if (((fileData.IndexOf("Function Description") + 1) 
                                        == 0)) {
                                //                                 'CloseProcess("WerFault.exe")
                                runStatus = arrData(18);
                                if ((runStatus.ToUpper() == "YES")) {
                                    // msgbox(fileData)
                                    scenarioID = arrData(2);
                                    scenarioDescription = arrData(3);
                                    scripID = arrData(8);
                                    stepID = arrData(10);
                                    //                                     If stepID = "Step 899" Then MsgBox("Debug")
                                    stepDescription = arrData(11);
                                    stepExpectedResult = arrData(15);
                                    objectMapName = arrData(17);
                                    fieldName = arrData(12);
                                    keyword = arrData(15).ToUpper();
                                    FieldConstruction = arrData(19);
                                    inputdata = arrData(13).Replace("^", ",");
                                    // If prevScriptID <> scripID And recoveryFlag = 1 Then
                                    //                                         If prevScriptID <> scenarioID And recoveryFlag = 1 Then
                                    //                                             recoveryFlag=0
                                    //                                             
                                    //                                         End If                
                                    //                                         If recoveryFlag=0 Then                        
                                    CloseProcess("WerFault.exe");
                                    Main.PerformAction();
                                    //                                         'CloseProcess("WerFault.exe")
                                    Main.WriteToOutputFile();
                                    prevScriptID = scenarioID;
                                    //                                         End If
                                }
                                
                            }
                            
                        }
                        
                        //                         WriteConsoleLog("******************************** ARTP " & scenarioname & " execution Finished BatchMode = " & strBatchMode & "********************************")
                    }
                    
                    objFSO_1 = System.IO.File.Create((executionLogPath 
                                    + (scenarioID + ("_" + "Output.csv"))));
                    objFSO_1.Close;
                    System.IO.File.Copy(outputFilePath, (executionLogPath 
                                    + (scenarioID + ("_" + "Output.csv"))), true);
                    Results();
                    Wait(TestCaseExecutionIntervalTime);
                    Main.generateExecutionLog();
                    Wait(TestCaseExecutionIntervalTime);
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("Main Exception generated " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                WriteConsoleLog("Main WriteToOutputFile");
                Main.WriteToOutputFile();
                Main.generateExecutionLog();
            }
            
            if (!(objFile == null)) {
                objFile.Close;
            }
            
            if (!(objOutputFile == null)) {
                objOutputFile.Close;
            }
            
        }
        
        if (!(DebugLogFile == null)) {
            WriteConsoleLog("******************************** ARTP execution Finished ********************************");
            DebugLogFile.Close;
        }
        
    }
    
    public static void PerformAction() {
        //     'CloseProcess("WerFault.exe")
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                watch.Start();
                screenShotPath = (snapshotsPath 
                            + (stepID + ".png"));
                if (((keyword.IndexOf("WAIT") + 1) 
                            > 0)) {
                    Wait();
                }
                else if (((keyword.IndexOf("ARITHMETIC") + 1) 
                            > 0)) {
                    ARITHMETIC();
                }
                else if (((keyword.IndexOf("INCREMENTBY") + 1) 
                            > 0)) {
                    IncrementBy();
                }
                else if (((keyword.IndexOf("INCREMENTDATE") + 1) 
                            > 0)) {
                    IncrementDate();
                }
                else if (((keyword.IndexOf("DECREMENTBY") + 1) 
                            > 0)) {
                    DecrementBy();
                }
                else if (((keyword.IndexOf("SPLIT") + 1) 
                            > 0)) {
                    SplitAndStoreInDB();
                }
                else if (((keyword.IndexOf("CONCATENATE") + 1) 
                            > 0)) {
                    ConcatenateaAndStoreInDB();
                }
                else if (((keyword.IndexOf("OPENNEWTAB") + 1) 
                            > 0)) {
                    OpenNewTab();
                }
                else if (((keyword.IndexOf("CLOSETAB") + 1) 
                            > 0)) {
                    CloseTab();
                }
                else if (((keyword.IndexOf("LAUNCHSOCKETCLIENT") + 1) 
                            > 0)) {
                    LaunchSocketClient();
                }
                else if (((keyword.IndexOf("LAUNCHWINAPPLICATION") + 1) 
                            > 0)) {
                    LaunchPutty();
                }
                else if (((keyword.IndexOf("LAUNCHAPPLICATION") + 1) 
                            > 0)) {
                    Main.LaunchApplication();
                }
                else if (((keyword.IndexOf("PLINKFUNCTION") + 1) 
                            > 0)) {
                    pLinkFunction();
                }
                else if (((keyword.IndexOf("STOREBACKENDOUTPUT") + 1) 
                            > 0)) {
                    pLinkFunctionStoreOutputFromSQL();
                }
                else if (((keyword.IndexOf("STORELASTBACKENDOUTPUT") + 1) 
                            > 0)) {
                    pLinkFunctionStoreOutputFromSQLLast();
                }
                else if (((keyword.IndexOf("COPYFILE") + 1) 
                            > 0)) {
                    CopyFileFromRemoteServer();
                }
                else if (((keyword.IndexOf("VERIFYDATAINREPORT") + 1) 
                            > 0)) {
                    VerifyDataInReport();
                }
                else if (((keyword.IndexOf("RETRIVEDATAFROMREPORT") + 1) 
                            > 0)) {
                    RetrieveDataFromReport();
                }
                else if (((keyword.IndexOf("CLOSEAPP") + 1) 
                            > 0)) {
                    CloseApp();
                }
                else if (((keyword.IndexOf("SENDKEYS") + 1) 
                            > 0)) {
                    SendKeys();
                }
                else {
                    if (((keyword.IndexOf("CONDITIONAL") + 1) 
                                > 0)) {
                        WriteConsoleLog("Setting CONDITIONAL as True");
                        ConditionalFlag = true;
                    }
                    
                    WriteConsoleLog(("Value of CONDITIONAL is " + ConditionalFlag));
                    WriteConsoleLog("PerformAction Generating objvalue using FieldConstruction");
                    object pos1 = (FieldConstruction.IndexOf("[") + 1);
                    object pos2 = (FieldConstruction.IndexOf("]") + 1);
                    object splitFields = fieldConstruction.Split("//");
                    object field = "";
                    for (splitfield : splitFields) {
                        if (((splitfield.IndexOf("[") + 1) 
                                    == 0)) {
                            field = (field + splitfield);
                        }
                        else {
                            field = (field + splitfield.Substring(0, ((splitfield.IndexOf("[") + 1) 
                                            - 1)));
                        }
                        
                    }
                    
                    WriteConsoleLog(("PerformAction Generating objvalue using FieldConstruction inside Switch Case " + field));
                    switch (field) {
                        case "FormsWindow":
                            DynamicParentWait(silkTest.FormsWindow(objectMapName));
                            objValue = silkTest.FormsWindow(objectMapName);
                            break;
                        case "WindowBrowserWindow":
                            DynamicParentWait(silkTest.Window(objectMapName).BrowserWindow("BrowserWindow"));
                            objValue = silkTest.Window(objectMapName).BrowserWindow("BrowserWindow");
                            break;
                        case "BrowserApplicationBrowserWindow":
                            objValue = silkTest.BrowserApplication(objectMapName).BrowserWindow("BrowserWindow");
                            break;
                        case "BrowserApplicationWindowBrowserWindow":
                            DynamicParentWait(silkTest.BrowserApplication(objectMapName).Window("Window").BrowserWindow("BrowserWindow"));
                            objValue = silkTest.BrowserApplication(objectMapName).Window("Window").BrowserWindow("BrowserWindow");
                            break;
                        case "BrowserApplicationWindow2BrowserWindow":
                            DynamicParentWait(silkTest.BrowserApplication(objectMapName).Window("Window2").BrowserWindow("BrowserWindow"));
                            objValue = silkTest.BrowserApplication(objectMapName).Window("Window2").BrowserWindow("BrowserWindow");
                            break;
                        case "BrowserApplicationWindowDialog":
                            DynamicParentWait(silkTest.BrowserApplication(objectMapName).Window("Window").Dialog("Dialog"));
                            objValue = silkTest.BrowserApplication(objectMapName).Window("Window").Dialog("Dialog");
                            break;
                        case "BrowserApplicationDialog":
                            WriteConsoleLog("Dialog dynamic wait started");
                            if ((ConditionalFlag == true)) {
                                maxWaitInSeconds = 3;
                            }
                            else {
                                maxWaitInSeconds = 10;
                            }
                            
                            // For cnt = 1 To maxWaitInSeconds
                            // WriteConsoleLog("Dialog dynamic wait started. Attempt " & cnt)
                            // Try
                            DynamicParentWait(silkTest.BrowserApplication(objectMapName).Dialog("Dialog"));
                            // Catch
                            //     Wait(1)                        
                            // End Try
                            // Next                
                            objValue = silkTest.BrowserApplication(objectMapName).Dialog("Dialog");
                            break;
                        case "Dialog":
                            DynamicParentWait(silkTest.Dialog(objectMapName));
                            objValue = silkTest.Dialog(objectMapName);
                            break;
                        case "WindowDialog":
                            if ((FieldConstruction.IndexOf("Message from webpage") + 1)) {
                                DynamicParentWait(silkTest.Window(objectMapName).Dialog("Message from webpage"));
                                objValue = silkTest.Window(objectMapName).Dialog("Message from webpage");
                            }
                            else {
                                DynamicParentWait(silkTest.Window(objectMapName).Dialog("Dialog"));
                                objValue = silkTest.Window(objectMapName).Dialog("Message from webpage");
                            }
                            
                            break;
                        case "Window":
                            DynamicParentWait(silkTest.Window(objectMapName));
                            objValue = silkTest.Window(objectMapName);
                            //             Case "WindowMessagefromwebpage"
                            //                 Call DynamicParentWait(silkTest.Window(objectMapName))                
                            //                 objValue = silkTest.Window(objectMapName)
                            break;
                        default:
                            WriteConsoleLog("PerformAction incorrect Field construction entered");
                            resultFlag = "Terminated";
                            actualMessage = "Incorrect Field construction entered";
                            Main.WriteToOutputFile();
                            break;
                    }
                    WriteConsoleLog("PerformAction Generating objvalue using FieldConstruction inside Switch Case");
                    if ((((keyword.IndexOf("LAUNCHAPPLICATION") + 1) 
                                == 0) 
                                && (((keyword.IndexOf("SPLIT") + 1) 
                                == 0) 
                                && (((keyword.IndexOf("CONCATENATE") + 1) 
                                == 0) 
                                && (((keyword.IndexOf("WAIT") + 1) 
                                == 0) 
                                && (((keyword.IndexOf("WININPUT") + 1) 
                                == 0) 
                                && (((keyword.IndexOf("WINPRESSKEY") + 1) 
                                == 0) 
                                && ((keyword.IndexOf("WINRETINPUT") + 1) 
                                == 0)))))))) {
                        //             silkTest.WaitForObject(objValue.GenerateLocator,30000)
                        if ((keyword != "SWITCHTAB")) {
                            DynamicObjectWait();
                            // If inputdata <> "Filed dosenot Exist" Then
                            // Dim temp As Boolean
                            className = objValue.TestObject(fieldName).GetType.Name;
                            // className
                            // End If
                        }
                        
                    }
                    
                    maxWaitInSeconds = 10;
                    // msgbox(keyword)
                    switch (keyword.ToUpper()) {
                        case "CONDITIONAL":
                            maxWaitInSeconds = 3;
                            Main.Input();
                            break;
                        case "INPUT":
                            Main.Input();
                            break;
                        case "INPUTWITHOUTCLEARTEXT":
                            Main.InputWithoutClearText();
                            break;
                        case "DATEINPUT":
                            DateInput();
                            break;
                        case "INPUTLIST":
                            Inputlist();
                            break;
                        case "INPUTLISTDOWNARROW":
                            InputlistDownArrow();
                            break;
                        case "VERIFY":
                            Main.Verify();
                            break;
                        case "TABLEINPUT":
                            Main.TableInput();
                            break;
                        case "TABLERETRIEVE":
                            TableRetrieve();
                            break;
                        case "TABLEDBLCLICK":
                            Main.TableDoubleClick();
                            break;
                        case "LAUNCHAPPLICATION":
                            Main.LaunchApplication();
                            break;
                        case "LAUNCHWINAPPLICATION":
                            LaunchPutty();
                            break;
                        case "WININPUT":
                            PuttyInput();
                            break;
                        case "VERIFYFIELDPROPERTY":
                            VerifyFieldProperty();
                            break;
                        case "WINRETINPUT":
                            PuttyRetInput();
                            break;
                        case "WINPRESSKEY":
                            PuttyPressKey();
                            break;
                        case "OPENNEWTAB":
                            OpenNewTab();
                            break;
                        case "CLOSETAB":
                            CloseTab();
                            break;
                        case "SWITCHTAB":
                            SwitchTab();
                            break;
                        case "RETSTORE":
                            Main.RetStore();
                            break;
                        case "RETINPUT":
                            Main.RetInput();
                            break;
                        case "VERIFYWITHDB":
                            Main.VerifywithDB();
                            break;
                        case "VARIABLECOMPARE":
                            VariableCompare();
                            break;
                        case "CONCATENATE":
                            ConcatenateaAndStoreInDB();
                            break;
                        case "SPLIT":
                            SplitAndStoreInDB();
                            break;
                        case "WAIT":
                            Wait();
                            break;
                        case "TABLEINPUTFROMDB":
                            TableInputFromDB();
                            break;
                        case "PRESSKEY":
                            PressKey();
                            break;
                        case "MOUSEKEY":
                            MouseKey();
                            break;
                        case "ARITHMETIC":
                            ARITHMETIC();
                            break;
                        case "IncrementBy":
                            IncrementBy();
                            break;
                        case "DecrementBy":
                            DecrementBy();
                            break;
                        case "CONCATANDINPUT":
                            ConcatenateandInput();
                            break;
                        case "TABLEROWRETSTORE":
                            TableRowRetStore();
                            break;
                        case "TABLESEARCH":
                            TableSearch();
                            break;
                        case "TABLEROWINPUT":
                            TableRowInput();
                            break;
                        case "TABLEROWRETRIEVE":
                            TableRowRetrieve();
                            break;
                        case "RETRIEVETABLEROW":
                            RetrieveTableRow();
                            break;
                        case "FILEUPLOAD":
                            fileUploadVbs();
                            break;
                        case "TABLEROWINPUTDB":
                            TableRowInputDB();
                            //             Case "TABLEROWNOTEXSITS"    
                            //                 Call TableRowNotExsits()
                            break;
                        case "MODIFYDATE":
                            ModifyDate();
                            // Case "INCREMENTDATE"    
                            //     Call IncrementDate()
                            break;
                        case "CONVERTDATE":
                            ConvertDate();
                            break;
                        case "INCREMENTTIME":
                            IncrementTime();
                            break;
                        case "RANDOMINPUT":
                            RandomInput();
                            break;
                        case "GENERATERANDOM":
                            GenerateRandom();
                            break;
                        case "SOCKETCLIENTNAVIGATE":
                            SocketClientNavigate();
                            break;
                        case "SOCKETCLIENTMESSAGETOSERVER":
                            SocketClientMessageToServer();
                            break;
                        case "SOCKETCLIENTMESSAGEFROMSERVER":
                            SocketClientMessageFromServer();
                            break;
                        case "SOCKETCLIENTINPUT":
                            SocketClientInput();
                            break;
                        case "SOCKETCLIENTWEBINPUT":
                            SocketClientWebInput();
                            break;
                        case "RANDOMFOURDIGITINPUT":
                            RandomFourDigitInput();
                            break;
                        case "RANDOMTHREEDIGITINPUT":
                            RandomThreeDigitInput();
                            break;
                        case "RANDOMLEDNO":
                            RandomLEDNO();
                            break;
                        case "RANDOMSERIALNO":
                            RandomSerialNO();
                            break;
                        case "DOUBLECLICK":
                            DoubleClick();
                            break;
                        case "WINDOWRETINPUT":
                            WindowRetInput();
                            break;
                        case "WINDOWINPUT":
                            WindowInput();
                            break;
                        case "REPLACESTRING":
                            ReplaceString();
                            break;
                        case "PLINKFUNCTION":
                            pLinkFunction();
                            break;
                        case "TYPEDATA":
                            TypeData();
                            break;
                        case "TABLEDOUBLECLICKFROMDB":
                            Main.TableDoubleClickFromDB();
                            //             Case "SUBSCRIPT"
                            //                 Call Subscript()
                            // Case "INPUTKEYBOARDEVENT"    
                            // Call InputKeyBoardEvent()
                            break;
                        case "RANDOMAADHARENROLLDTLS":
                            RANDOMAADHARENROLLDTLS();
                            break;
                        case "RANDOMAADHARNUMBER":
                            RANDOMAADHARNUMBER();
                            break;
                        case "FIELDDOESNOTEXISTS":
                            Main.FieldDoesnotExist();
                            break;
                        case "RANDOMCORPIDNUM":
                            RANDOMCORPIDNUM();
                            break;
                        case "INPUTLISTPRESSKEYS":
                            InputListPressKeys();
                            break;
                        default:
                            WriteConsoleLog("PerformAction incorrect Keyword entered");
                            resultFlag = "Terminated";
                            actualMessage = "incorrect Keyword entered";
                            recoveryFlag = 1;
                            Main.WriteToOutputFile();
                            break;
                    }
                }
                
                WriteConsoleLog(("PerformAction Capturing Screenshot at " + screenShotPath));
                silkTest.CaptureBitmap(screenShotPath);
                WriteConsoleLog("PerformAction Screenshot is captured");
            }
            catch (Exception ex) {
                WriteConsoleLog(("PerformAction exception generated. " + ex.Message));
                WriteConsoleLog(("PerformAction Capturing Screenshot at " + screenShotPath));
                silkTest.CaptureBitmap(screenShotPath);
                WriteConsoleLog("PerformAction exception generated. Screenshot captured");
                if ((((ex.Message.IndexOf("not find") + 1) 
                            > 0) 
                            && (ConditionalFlag == true))) {
                    WriteConsoleLog(("PerformAction exception generated ConditionalFlag " + ConditionalFlag));
                    // Do nothing
                    resultFlag = "Pass";
                    actualMessage = "Conditional Object was not found";
                }
                else {
                    WriteConsoleLog(("PerformAction exception generated ConditionalFlag " + ConditionalFlag));
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                }
                
                WriteConsoleLog("Perform Action WriteToOutputFile");
                Main.WriteToOutputFile();
            }
            finally {
                ConditionalFlag = false;
                WriteConsoleLog(("Setting CONDITIONAL as " + ConditionalFlag));
            }
            
        }
        
        WriteConsoleLog("PerformAction finished");
    }
    
    public static void ConvertCalender() {
        WriteConsoleLog("ConvertCalender started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object intmonth;
                object newMonth;
                object listValue;
                object monthend;
                object arr;
                object getdate;
                object mname;
                object datayear;
                object intyear;
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                    newmonth = inputdataSplit(2);
                    monthend = inputdataSplit(3);
                    datayear = inputdataSplit(4);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                intmonth = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                strVariable = datayear;
                intyear = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                // msgbox (intyear)
                // msgbox (intmonth)
                objValue.DomListBox(fieldName).Select(0);
                listValue = objValue.DomListBox(fieldName).Text;
                // msgbox (listValue)
                // For Each x In listvalue
                // msgbox (listvalue(1))
                // Next
                if ((listValue == "January")) {
                    // fieldData  = monthname(Month,True)
                    mname = monthname(intmonth);
                }
                
                if ((listValue == "Jan")) {
                    mname = monthname(intmonth, true);
                }
                
                fieldData = mname.Trim();
                // msgbox (fieldData)
                inputdata = newmonth;
                Main.WriteToLookupFile();
                getDate = DateSerial(intyear, (intmonth + 1), 0);
                // msgbox (getDate)
                arr = getDate.Split("/");
                fieldData = arr[1];
                inputdata = monthend;
                Main.WriteToLookupFile();
            }
            catch (Exception ex) {
                WriteConsoleLog(("ConvertCalender exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ConvertCalender finished");
    }
    
    public static void ConvertCalenderOld() {
        // Converts the Month numeric value to Month name in Calender Table and saves the month end date in lookup file 
        // eg: SCR-LN-006;Month;New_Month;Month_End;Year
        // where SCR-LN-006 = Scenarioname
        // Month = use split Function In server Date For month 
        // New Month = variable to store month name
        // Month_End= variable to store End of month
        // Year = use split Function In server Date For Year
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object intmonth;
                object newMonth;
                object listValue;
                object monthend;
                object arr;
                object getdate;
                object mname;
                object datayear;
                object intyear;
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                    newmonth = inputdataSplit(2);
                    monthend = inputdataSplit(3);
                    datayear = inputdataSplit(4);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                intmonth = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                strVariable = datayear;
                intyear = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                // msgbox (intyear)
                // msgbox (intmonth)
                listValue = objValue.DomListBox(fieldName).text;
                // 'msgbox (listvalue)
                if ((listValue == "January")) {
                    // fieldData  = monthname(Month,True)
                    mname = monthname(intmonth);
                }
                
                if ((listValue == "Jan")) {
                    mname = monthname(intmonth, true);
                }
                
                fieldData = mname.Trim();
                // msgbox (fieldData)
                inputdata = newmonth;
                Main.WriteToLookupFile();
                getDate = DateSerial(intyear, (intmonth + 1), 0);
                // msgbox (getDate)
                arr = getDate.Split("/");
                fieldData = arr[1];
                inputdata = monthend;
                Main.WriteToLookupFile();
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
    }
    
    public static void WriteToOutputFile() {
        try {
            WriteConsoleLog("WriteToOutputFile started");
            inputdata = inputdata.Replace(",", "^");
            actualMessage = actualMessage.Replace(",", " ^");
            stepExpectedResult = stepExpectedResult.Replace(",", "^");
            WriteConsoleLog("Writing Data");
            //         WriteConsoleLog("scenarioID - " & scenarioID )
            //         WriteConsoleLog("scenarioDescription - " & scenarioDescription )
            //         WriteConsoleLog("scripID - " & scripID )
            //         WriteConsoleLog("stepID - " & stepID )
            //         WriteConsoleLog("stepDescription - " & stepDescription )
            //         WriteConsoleLog("stepExpectedResult - " & stepExpectedResult )
            //         WriteConsoleLog("actualMessage - " & actualMessage )
            //         WriteConsoleLog("objectMapName - " & objectMapName )
            //         WriteConsoleLog("fieldName - " & fieldName )
            //         WriteConsoleLog("keyword - " & keyword )
            //         WriteConsoleLog("inputdata - " & inputdata )
            //         WriteConsoleLog("resultFlag - " & resultFlag )
            //         WriteConsoleLog("screenShotPath - " & screenShotPath )
            if ((objFile == null)) {
                WriteConsoleLog("objOutputFile is nothing");
            }
            else {
                WriteConsoleLog("objOutputFile is there");
            }
            
            // scenarioID & "," &  scenarioDescription & ","  & scripID & "," & stepID & "," & stepDescription & "," & stepExpectedResult & ","& actualMessage & "," & objectMapName & "," & fieldName & ","& keyword & "," & inputdata & "," & resultFlag & "," & screenShotPath
            objOutputFile.WriteLine((scenarioID + ("," 
                            + (scenarioDescription + ("," 
                            + (scripID + ("," 
                            + (stepID + ("," 
                            + (stepDescription + ("," 
                            + (stepExpectedResult + ("," 
                            + (actualMessage + ("," 
                            + (objectMapName + ("," 
                            + (fieldName + ("," 
                            + (keyword + ("," 
                            + (inputdata + ("," 
                            + (resultFlag + ("," + screenShotPath)))))))))))))))))))))))));
            WriteConsoleLog("WriteToOutputFile finished");
        }
        catch (Exception ex) {
            WriteConsoleLog(("WriteToOutputFile exception generated " + ex.Message));
        }
        
    }
    
    public static void generateExecutionLog() {
        WriteConsoleLog("generateExecutionLog started");
        try {
            Process ps;
            for (ps : Process.GetProcesses) {
                if ((ps.ProcessName == "WINWORD")) {
                    ps.Kill;
                }
                
            }
            
        }
        catch (Exception ex1) {
            WriteConsoleLog(("generateExecutionLog Exception generated " + ex1.Message));
        }
        
        WriteConsoleLog("Called Close Process");
        CloseProcess("WINWORD.EXE");
        watch.Stop();
        double exetime;
        exetime = System.Math.Round(watch.Elapsed.TotalMinutes, 3);
        psi.UseShellExecute = true;
        psi.FileName = generateLogsVbsPath;
        psi.Arguments = (executionLogPath + (" " 
                    + (outputFilePath + (" " 
                    + (scenarioID + (" " + exetime))))));
        Workbench.ResultComment(executionLogPath);
        Process.Start(psi);
        WriteConsoleLog("generateExecutionLog finished. generateLogsVbs file is called");
    }
    
    public static void createOutputFolder(string Screnario_Name) {
        outputDatePath = (logFolderPath + DateTime.Today.ToString("dd-MMM-yyyy"));
        if (!System.IO.Directory.Exists(outputDatePath)) {
            System.IO.Directory.CreateDirectory(outputDatePath);
        }
        
        outputPath = (outputDatePath + ("\\" + Screnario_Name));
        if (!System.IO.Directory.Exists(outputPath)) {
            System.IO.Directory.CreateDirectory(outputPath);
        }
        
        for (int i = 1; (i <= 500); i++) {
            runNumberFolder = (outputPath + "\\Run");
            if (!System.IO.Directory.Exists((runNumberFolder + i))) {
                System.IO.Directory.CreateDirectory((runNumberFolder + i));
                snapshotsPath = (runNumberFolder 
                            + (i + "\\Snapshots\\"));
                System.IO.Directory.CreateDirectory(snapshotsPath);
                executionLogPath = (runNumberFolder 
                            + (i + "\\Executionlog\\"));
                System.IO.Directory.CreateDirectory(executionLogPath);
                break;
            }
            
        }
        
    }
    
    public static void initConfigurations() {
        object strDateTime = Now.ToString();
        strDateTime = strDateTime.Replace("/", "-");
        strDateTime = strDateTime.Replace(":", ".");
        // MsgBox(strDateTime)
        object OldlookupFilePathForWrite;
        outputFilePath = (path + "\\Output_File\\Output.csv");
        logFolderPath = (path + "\\Execution_Logs\\");
        generateLogsVbsPath = (path + "\\Vbs_Files\\GenerateLogs.vbs");
        objectMapFolderPath = (path + "\\Export Object Map\\");
        lookupFilePath = (path + "\\Lookup_File\\");
        lookupFilePathForWrite = (path + "\\Lookup_File\\Lookup_File.csv");
        OldlookupFilePathForWrite = (path + ("\\Lookup_File\\oldLookup_Files\\Lookup_File_" 
                    + (strDateTime + ".csv")));
        objectMapFileName = "ObjectMap.csv";
        lookupFileName = "Lookup_File.csv";
        if (System.IO.File.Exists(lookupFilePathForWrite)) {
            DateTime fileDate;
            // fileDate = System.IO.File.GetCreationTime(lookupFilePathForWrite)
            fileDate = System.IO.File.GetLastWriteTime(lookupFilePathForWrite);
            if ((fileDate.Date < System.DateTime.Today)) {
                // System.IO.File.Delete(lookupFilePathForWrite)
                if (System.IO.File.Exists(OldlookupFilePathForWrite)) {
                    System.IO.File.Delete(OldlookupFilePathForWrite);
                }
                
                System.IO.File.Move(lookupFilePathForWrite, OldlookupFilePathForWrite);
                objFSO = createobject("Scripting.FileSystemObject");
                objLookupFile = objFSO.CreateTextFile(lookupFilePathForWrite);
                objLookupFile.WriteLine(("Scenario_Id" + ("," + ("Variable" + ("," + "Value")))));
                objLookupFile.Close();
            }
            
        }
        
        //     WriteConsoleLog("initConfigurations finished")        
    }
    
    public static void GetCsvData(void strFolderPath, void strFileName, void fieldName, void objectMapName) {
        WriteConsoleLog("GetCsvData started");
        string strConnString = ("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" 
                    + (objectMapFolderPath + ";Extended Properties=\'text;HDR=Yes;FMT=Delimited(,)\';"));
        OleDbConnection conn = new OleDbConnection(strConnString);
        try {
            conn.Open();
            string Query = ("SELECT * FROM [" 
                        + (objectMapFileName + ("] where Field_Name =\'" 
                        + (FieldName + ("\' and ObjectMap_Name =\'" 
                        + (objectMapName + "\'"))))));
            OleDbCommand cmd = new OleDbCommand(Query, conn);
            OleDbDataAdapter da = new OleDbDataAdapter(cmd);
            da.SelectCommand = cmd;
            DataSet ds = new DataSet();
            da.Fill(ds);
            da.Dispose;
            return ds;
        }
        catch (Exception ex) {
            WriteConsoleLog(("GetCsvData exception generated. " + ex.Message));
            return null;
        }
        finally {
            conn.Close();
        }
        
        WriteConsoleLog("GetCsvData finished");
    }
    
    public static void Sendkeys() {
        WriteConsoleLog("Sendkeys started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                // Dim Wsc As Type = Type.GetTypeFromProgID("WScript.Shell")
                // Dim Wsc=CreateObject("Wscript.shell")
                // Dim Wsc As
                // Set Wsc = CreateObject("Wscript.shell")
                DynamicObjectWait();
                switch (className) {
                    case "DomTextField":
                        objValue.DomTextField(fieldName).Click;
                        objValue.DomTextField(fieldName).TypeKeys(inputdata);
                        // wsc.Sendkeys(inputdata)
                        // Microsoft.VisualBasic.Devices.Keyboard K As Microsoft.VisualBasic.Devices.Keyboard
                        // System.Windows.Forms.SendKeys(inputdata)
                        resultFlag = "Pass";
                        break;
                    default:
                        resultFlag = "Terminated";
                        actualMessage = "Please enter the correct keyword";
                        recoveryFlag = 1;
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("Input exception generated. " + ex.Message));
                if ((((ex.Message.IndexOf("not find") + 1) 
                            > 0) 
                            && (ConditionalFlag == true))) {
                    WriteConsoleLog(("Input exception generated and ConditionalFlag " + ConditionalFlag));
                    // Do nothing
                    resultFlag = "Pass";
                }
                else {
                    WriteConsoleLog(("Input exception generated and ConditionalFlag " + ConditionalFlag));
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                }
                
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("Sendkey finished");
    }
    
    public static void Input() {
        WriteConsoleLog("Input started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            if (((fieldName.IndexOf("Phone") + 1) 
                        > 0)) {
                inputdata = ("0" + inputdata);
            }
            
            if (((fieldName.IndexOf("MobileNo With Plus Sign") + 1) 
                        > 0)) {
                inputdata = ("+91" + inputdata);
            }
            
            object text1;
            object bFlag;
            object ScreenNumber;
            object recoveryobj1;
            try {
                DynamicObjectWait();
                switch (className) {
                    case "DomTextField":
                        inputdata = inputdata.Trim();
                        //                         
                        //                         If inputdata = "1000038"  Then
                        //                             inputdata = "1187"
                        //                         End If
                        //                         If inputdata = "1000039" Then
                        //                             inputdata = "43703"
                        //                         End If                                                
                        //                         
                        //                         If inputdata = "9920049" Then
                        //                             inputdata = "1187" '820
                        //                         End If
                        //                         If inputdata = "9920099" Or inputdata = "1733" Then
                        //                             inputdata = "1251" '43705
                        //                         End If                    
                        // 
                        if (inputdata.ToString.Contains("#")) {
                            if ((inputdata.ToString.Split("#").Length > 1)) {
                                object temptext = inputdata.ToString.Split("#")[1];
                                inputdata = temptext;
                            }
                            
                        }
                        
                        if (((fieldName.IndexOf("User No") + 1) 
                                    > 0)) {
                            TellerNo = inputdata;
                            // Save the Teller No to reset in putty
                        }
                        
                        // msgbox (inputdata)
                        //                     Call DynamicObjectWait()
                        objValue.DomTextField(fieldName).Click;
                        objValue.DomTextField(fieldName).ClearText;
                        objValue.DomTextField(fieldName).SetText("");
                        if ((inputdata.Trim() == "")) {
                            return;
                        }
                        
                        object arrInputdata;
                        if (((inputdata.IndexOf("/") + 1) 
                                    > 0)) {
                            arrInputdata = inputdata.Split("/");
                            if ((arrInputdata[0].Length == 1)) {
                                arrInputdata[0] = (("0" + arrInputdata[0].ToString())).ToString();
                                inputdata = (arrInputdata[0] + ("/" 
                                            + (arrInputdata[1] + ("/" + arrInputdata[2]))));
                            }
                            
                            if ((arrInputdata[1].Length == 1)) {
                                arrInputdata[1] = (("0" + arrInputdata[1].ToString())).ToString();
                                inputdata = (arrInputdata[0] + ("/" 
                                            + (arrInputdata[1] + ("/" + arrInputdata[2]))));
                            }
                            
                        }
                        
                        objValue.DomTextField(fieldName).SetText(inputdata);
                        objValue.DomTextField(fieldName).Click;
                        object currfielddata = objValue.DomTextField(fieldName).Text.Trim();
                        if ((currfielddata == "")) {
                            objValue.DomTextField(fieldName).TypeKeys(inputdata);
                        }
                        
                        currfielddata = objValue.DomTextField(fieldName).Text.Trim();
                        if ((currfielddata == "")) {
                            resultFlag = "Fail";
                            actualMessage = ("Actual value : " 
                                        + (currfielddata + (". Expected value : " + inputdata)));
                        }
                        else {
                            resultFlag = "Pass";
                        }
                        
                        break;
                    case "DomButton":
                        try {
                            objValue.DomButton(fieldName).DomClick;
                            // Date: 05.03.2022
                            // Error Desc: Message in bottom was not enabled on suervisory screen. Object not enabled error appears at line no 979.
                            // Change Desc: To handle both the dropdown buttons in CBS
                            // Author: Ankush / Sunil
                            if (silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("Message").IsFocused) {
                                object ServerMsg = silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("Message").GetText();
                            }
                            
                            if ((fieldName == "Login")) {
                                WriteConsoleLog("Checking whether the field is login and the teller is already logged in");
                                if (((ServerMsg.IndexOf("MULTIPLE TELLER LOGIN NOT ALLOWED") + 1) 
                                            > 0)) {
                                    WriteConsoleLog("Teller is already logged in, trying to reset");
                                    ResetTellerWithLoggedInutty(TellerNo);
                                    WriteConsoleLog("Teller is now reset");
                                }
                                
                                if (((ServerMsg.IndexOf("SERVER IS BUSY") + 1) 
                                            > 0)) {
                                    WriteConsoleLog("Application login failed");
                                    actualMessage = ("Application login failed. " + ServerMsg);
                                    resultFlag = "Terminated";
                                }
                                
                                if (((ServerMsg.IndexOf("APPLICATION Not ACTIVE.") + 1) 
                                            > 0)) {
                                    WriteConsoleLog("Application login failed");
                                    actualMessage = ("Application login failed. " + ServerMsg);
                                    resultFlag = "Terminated";
                                }
                                
                            }
                            
                        }
                        catch (Exception ex1) {
                            if ((fieldName.ToUpper() == "UPDATELATER")) {
                                DynamicParentWait(silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("CloseNow"));
                                silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("CloseNow").DomClick();
                            }
                            
                            if ((fieldName.ToUpper() == "CLOSENOW")) {
                                DynamicParentWait(silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("Updatelater"));
                                silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("Updatelater").DomClick();
                            }
                            
                            if (((ex1.Message.IndexOf("not find") + 1) 
                                        > 0)) {
                                // Do nothing
                            }
                            
                        }
                        
                        break;
                    case "DomCheckBox":
                        objValue.DomCheckBox(fieldName).DomClick;
                        resultFlag = "Pass";
                        break;
                    case "DomElement":
                        if ((objValue.DomElement(fieldName).GetDomAttribute("title") == "Please enter the highest amount")) {
                            objValue.DomElement(fieldName).TypeKeys(inputdata);
                            // Date: 10.03.2022
                            // Error Desc: Screen 070711 - Unable to enter data in text field. Object identified as DOM Element.
                            // Change Desc: if FNSType DOM Attribute as fieldinput, data should be entered.
                            // Author: Ankush / Siddhesh
                        }
                        else if ((objValue.DomElement(fieldName).GetDomAttribute("FNSType") == "fieldinput")) {
                            objValue.DomElement(fieldName).TypeKeys(inputdata);
                        }
                        else {
                            objValue.DomElement(fieldName).DomClick;
                        }
                        
                        resultFlag = "Pass";
                        break;
                    case "DomLink":
                        objValue.DomLink(fieldName).DomClick;
                        resultFlag = "Pass";
                        break;
                    case "DomRadioButton":
                        objValue.DomRadioButton(fieldName).Select();
                        resultFlag = "Pass";
                        break;
                    case "PushButton":
                        objValue.PushButton(fieldName).Click;
                        resultFlag = "Pass";
                        break;
                    default:
                        resultFlag = "Terminated";
                        actualMessage = "Please enter the correct keyword";
                        recoveryFlag = 1;
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("Input exception generated. " + ex.Message));
                if ((((ex.Message.IndexOf("not find") + 1) 
                            > 0) 
                            && (ConditionalFlag == true))) {
                    WriteConsoleLog(("Input exception generated and ConditionalFlag " + ConditionalFlag));
                    // Do nothing
                    resultFlag = "Pass";
                }
                else {
                    WriteConsoleLog(("Input exception generated and ConditionalFlag " + ConditionalFlag));
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                }
                
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("Input finished");
    }
    
    public static void InputWithoutClearText() {
        // #### This function is copied from input function just to fix the issue as below
        // ### 1 while entering loan account number in manual charge collection screen it was throwing popup "account number can not be blank"
        //         because the method was clearing text field before entering number. so in this same method rempving clear text
        WriteConsoleLog("Input started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            if (((fieldName.IndexOf("Phone") + 1) 
                        > 0)) {
                inputdata = ("0" + inputdata);
            }
            
            if (((fieldName.IndexOf("MobileNo With Plus Sign") + 1) 
                        > 0)) {
                inputdata = ("+91" + inputdata);
            }
            
            object text1;
            object bFlag;
            object ScreenNumber;
            object recoveryobj1;
            try {
                DynamicObjectWait();
                switch (className) {
                    case "DomTextField":
                        inputdata = inputdata.Trim();
                        //                         
                        //                         If inputdata = "1000038"  Then
                        //                             inputdata = "1187"
                        //                         End If
                        //                         If inputdata = "1000039" Then
                        //                             inputdata = "43703"
                        //                         End If                                                
                        //                         
                        //                         If inputdata = "9920049" Then
                        //                             inputdata = "1187" '820
                        //                         End If
                        //                         If inputdata = "9920099" Or inputdata = "1733" Then
                        //                             inputdata = "1251" '43705
                        //                         End If                    
                        // 
                        if (inputdata.ToString.Contains("#")) {
                            if ((inputdata.ToString.Split("#").Length > 1)) {
                                object temptext = inputdata.ToString.Split("#")[1];
                                inputdata = temptext;
                            }
                            
                        }
                        
                        if (((fieldName.IndexOf("User No") + 1) 
                                    > 0)) {
                            TellerNo = inputdata;
                            // Save the Teller No to reset in putty
                        }
                        
                        // msgbox (inputdata)
                        //                     Call DynamicObjectWait()
                        objValue.DomTextField(fieldName).Click;
                        // objValue.DomTextField(fieldName).ClearText
                        // ######################################################################################
                        // Call DynamicParentWait(silkTest.BrowserApplication("Manual Charge Collection").Dialog("Dialog").DomElement("OK"))
                        // silkTest.BrowserApplication("Manual Charge Collection").Dialog("Dialog").DomElement("OK").Click
                        // objValue.DomTextField(fieldName).SetText("")
                        if ((inputdata.Trim() == "")) {
                            return;
                        }
                        
                        object arrInputdata;
                        if (((inputdata.IndexOf("/") + 1) 
                                    > 0)) {
                            arrInputdata = inputdata.Split("/");
                            if ((arrInputdata[0].Length == 1)) {
                                arrInputdata[0] = (("0" + arrInputdata[0].ToString())).ToString();
                                inputdata = (arrInputdata[0] + ("/" 
                                            + (arrInputdata[1] + ("/" + arrInputdata[2]))));
                            }
                            
                            if ((arrInputdata[1].Length == 1)) {
                                arrInputdata[1] = (("0" + arrInputdata[1].ToString())).ToString();
                                inputdata = (arrInputdata[0] + ("/" 
                                            + (arrInputdata[1] + ("/" + arrInputdata[2]))));
                            }
                            
                        }
                        
                        objValue.DomTextField(fieldName).SetText(inputdata);
                        objValue.DomTextField(fieldName).Click;
                        object currfielddata = objValue.DomTextField(fieldName).Text.Trim();
                        if ((currfielddata == "")) {
                            objValue.DomTextField(fieldName).TypeKeys(inputdata);
                        }
                        
                        currfielddata = objValue.DomTextField(fieldName).Text.Trim();
                        if ((currfielddata == "")) {
                            resultFlag = "Fail";
                            actualMessage = ("Actual value : " 
                                        + (currfielddata + (". Expected value : " + inputdata)));
                        }
                        else {
                            resultFlag = "Pass";
                        }
                        
                        break;
                    case "DomButton":
                        try {
                            objValue.DomButton(fieldName).DomClick;
                            object ServerMsg = silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("Message").GetText();
                            if ((fieldName == "Login")) {
                                WriteConsoleLog("Checking whether the field is login and the teller is already logged in");
                                if (((ServerMsg.IndexOf("MULTIPLE TELLER LOGIN NOT ALLOWED") + 1) 
                                            > 0)) {
                                    WriteConsoleLog("Teller is already logged in, trying to reset");
                                    ResetTellerWithLoggedInutty(TellerNo);
                                    WriteConsoleLog("Teller is now reset");
                                }
                                
                                if (((ServerMsg.IndexOf("SERVER IS BUSY") + 1) 
                                            > 0)) {
                                    WriteConsoleLog("Application login failed");
                                    actualMessage = ("Application login failed. " + ServerMsg);
                                    resultFlag = "Terminated";
                                }
                                
                                if (((ServerMsg.IndexOf("APPLICATION Not ACTIVE.") + 1) 
                                            > 0)) {
                                    WriteConsoleLog("Application login failed");
                                    actualMessage = ("Application login failed. " + ServerMsg);
                                    resultFlag = "Terminated";
                                }
                                
                            }
                            
                        }
                        catch (Exception ex1) {
                            if ((fieldName.ToUpper() == "UPDATELATER")) {
                                DynamicParentWait(silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("CloseNow"));
                                silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("CloseNow").DomClick();
                            }
                            
                            if ((fieldName.ToUpper() == "CLOSENOW")) {
                                DynamicParentWait(silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("Updatelater"));
                                silkTest.Window("Update Later").BrowserWindow("BrowserWindow").DomButton("Updatelater").DomClick();
                            }
                            
                            if (((ex1.Message.IndexOf("not find") + 1) 
                                        > 0)) {
                                // Do nothing
                            }
                            
                        }
                        
                        break;
                    case "DomCheckBox":
                        objValue.DomCheckBox(fieldName).DomClick;
                        resultFlag = "Pass";
                        break;
                    case "DomElement":
                        if ((objValue.DomElement(fieldName).GetDomAttribute("title") == "Please enter the highest amount")) {
                            objValue.DomElement(fieldName).TypeKeys(inputdata);
                        }
                        else {
                            objValue.DomElement(fieldName).DomClick;
                        }
                        
                        resultFlag = "Pass";
                        break;
                    case "DomLink":
                        objValue.DomLink(fieldName).DomClick;
                        resultFlag = "Pass";
                        break;
                    case "DomRadioButton":
                        objValue.DomRadioButton(fieldName).Select();
                        resultFlag = "Pass";
                        break;
                    case "PushButton":
                        objValue.PushButton(fieldName).Click;
                        resultFlag = "Pass";
                        break;
                    default:
                        resultFlag = "Terminated";
                        actualMessage = "Please enter the correct keyword";
                        recoveryFlag = 1;
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("Input exception generated. " + ex.Message));
                if ((((ex.Message.IndexOf("not find") + 1) 
                            > 0) 
                            && (ConditionalFlag == true))) {
                    WriteConsoleLog(("Input exception generated and ConditionalFlag " + ConditionalFlag));
                    // Do nothing
                    resultFlag = "Pass";
                }
                else {
                    WriteConsoleLog(("Input exception generated and ConditionalFlag " + ConditionalFlag));
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                }
                
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("Input finished");
    }
    
    // Public Sub FieldExist()
    //     WriteConsoleLog("FieldExist started")    
    //     If resultFlag = "Pass" Or resultFlag = "Recover" Then
    //         Try
    //                     fieldData = objValue.DomElement(fieldName).Exists
    //                     
    //                 If fieldData = True Then
    //                     resultFlag="Fail"                    
    //                     WriteConsoleLog("******************* " & scenarioname & " Pass *******************")
    //                     Else
    //                         resultFlag="Pass"    
    //                         WriteConsoleLog("******************* " & scenarioname & " Fail *******************")
    //                 End If                
    //                  
    //         Catch ex As Exception
    //                 WriteConsoleLog("Verify exception generated. " + ex.Message)
    //                 actualMessage = Err.Description
    //                 Call SetRecoveryAndResultFlag()                    
    //                 'recoveryFlag=1
    //                 Call WriteToOutputFile()
    //         End Try
    //     End If            
    //     WriteConsoleLog("FieldExist finished")
    // End Sub
    public static void Verify() {
        WriteConsoleLog("Verify started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    Array tableArgs = inputdata.Split(";");
                    tempScriptID = tableArgs[0];
                    strVariable = tableArgs[1];
                    // msgbox (tempScriptID & strVariable)
                    retrieved_Data = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                    // msgbox (retrieved_Data)
                    inputdata = retrieved_Data;
                    if (inputdata.ToString.Contains("#")) {
                        if ((inputdata.ToString.Split("#").Length > 1)) {
                            object temptext = inputdata.ToString.Split("#")[1];
                            inputdata = temptext;
                        }
                        
                    }
                    
                }
                
                switch (className) {
                    case "DomTextField":
                        DynamicObjectWait();
                        fieldData = objValue.DomTextField(fieldName).Text;
                        break;
                    case "DomCheckBox":
                        DynamicObjectWait();
                        // Call DynamicWait(objValue.DomCheckBox(fieldName))
                        fieldData = objValue.DomCheckBox(fieldName).Text;
                        break;
                    case "DomListBox":
                        DynamicObjectWait();
                        // Call DynamicWait(objValue.DomListBox(fieldName))
                        fieldData = objValue.DomListBox(fieldName).Text;
                        break;
                    case "DomElement":
                        DynamicObjectWait();
                        Wait(3);
                        // Call DynamicWait(objValue.DomElement(fieldName))
                        object strMessage = "";
                        object exitLoop = true;
                        while (exitLoop) {
                            strMessage = objValue.DomElement(fieldName).Text;
                            if (((strMessage.IndexOf("Processing") + 1) 
                                        == 0)) {
                                exitLoop = false;
                                Wait(1);
                            }
                            
                        }
                        
                        fieldData = objValue.DomElement(fieldName).Text;
                        break;
                    case "DomRadioButton":
                        DynamicObjectWait();
                        // Call DynamicWait(objValue.DomRadioButton(fieldName))
                        fieldData = objValue.DomRadioButton(fieldName).Text;
                        break;
                    case "DomTable":
                        try {
                            // Call DynamicWait(objValue.DomTable(fieldName))
                            DynamicObjectWait();
                            Array tableArgs = inputdata.Split(":");
                            tempScriptID = tableArgs[0];
                            strVariable = tableArgs[1];
                            object colNo;
                            colNo = tableArgs[2];
                            inputdata = tableArgs[3];
                            retrieved_Data = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                            fieldData = objValue.DomTable(fieldName).GetCellText(retrieved_Data, colNo);
                        }
                        catch (Exception ex) {
                            actualMessage = Err.Description;
                            SetRecoveryAndResultFlag();
                            // recoveryFlag=1
                        }
                        
                        break;
                    case "Label":
                        DynamicObjectWait();
                        fieldData = objValue.Label(fieldName).Text;
                        break;
                }
                fieldData = fieldData.Trim();
                inputdata = inputdata.Trim();
                WriteConsoleLog(("fieldData " + fieldData));
                WriteConsoleLog(("inputdata " + inputdata));
                //                 If  inputdata = "1" Then 
                //                     fieldData = "1"
                //                 Else If inputdata = "0" Then
                //                     fieldData = "0"
                //                 End If
                WriteConsoleLog(("Actual data " + fieldData));
                WriteConsoleLog(("Expected data " + inputdata));
                // msgbox(fieldData & " : " & inputdata)
                if (((fieldData.IndexOf(inputdata) + 1) 
                            == 0)) {
                    resultFlag = "Fail";
                    WriteConsoleLog(("******************* " 
                                    + (scenarioname + " failed *******************")));
                }
                
                actualMessage = fieldData;
                stepExpectedResult = inputdata;
            }
            catch (Exception ex) {
                WriteConsoleLog(("Verify exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("Verify finished");
    }
    
    public static void FieldDoesnotExist() {
        WriteConsoleLog("FiledDoesnotExist started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            // Try
            if ((objValue.TestObject(fieldName).Exists == true)) {
                resultFlag = "Passed";
                WriteConsoleLog(("******************* " 
                                + (scenarioname + " Passed *******************")));
            }
            else {
                resultFlag = "Failed";
                WriteConsoleLog(("******************* " 
                                + (scenarioname + " Failed *******************")));
            }
            
            actualMessage = fieldData;
            stepExpectedResult = inputdata;
            // Catch ex As Exception
            // WriteConsoleLog("Verify exception generated. " + ex.Message)
            // actualMessage = Err.Description
            // Call SetRecoveryAndResultFlag()                    
            // recoveryFlag=1
            // Call WriteToOutputFile()
            // End Try
        }
        
        WriteConsoleLog("FiledDoesnotExist finished");
    }
    
    public static void LaunchApplication() {
        WriteConsoleLog("Calling CloseApp");
        // Call CloseApp()
        WriteConsoleLog("LaunchApplication started");
        WriteConsoleLog("Calling Close process to kill iexplore.exe");
        CloseProcess("iexplore.exe");
        System.Threading.Thread.Sleep(2000);
        WriteConsoleLog("Closing any open browser");
        BrowserBaseState IE = new BrowserBaseState(BrowserType.InternetExplorer, inputdata);
        BrowserApplication IEBrowser;
        //         'close any open browsers before launching new browser - IE
        //     Try
        //         Dim Browsers As List(Of BrowserApplication)
        //         Browsers = Silktest.FindAll(Of BrowserApplication)("/BrowserApplication")
        //         
        //         Dim Browser As BrowserApplication
        //         For Each Browser In Browsers                        
        //             Browser.SetActive()
        //             Browser.Close()
        //             CloseProcess("WerFault.exe")
        //             'Browser.CloseSynchron(True)
        //             CloseProcess("WerFault.exe")
        //         Next
        //     Catch ex As Exception
        //         WriteConsoleLog("LaunchApplication exception generated. " + ex.Message)
        //         WriteConsoleLog("Err.Description. " + Err.Description)
        //         resultFlag = "Terminated"
        //         actualMessage = Err.Description    
        //         recoveryFlag=1
        //     End Try
        //     CloseProcess("WerFault.exe")    
        IEBrowser = IE.Execute();
        IEBrowser.ClearCache(0);
        //     IEBrowser = IE.Execute()
        WriteConsoleLog("Executed IE.Execute");
        Agent.Attach("iexplore.exe", "");
        WriteConsoleLog("Attached iexplore.exe");
        CloseProcess("WerFault.exe");
        // Date: 10.03.2022
        // Error Desc: UserID Text Field unable to identify while page load is in progress.
        // Change Desc: Waiting for UserID Text Field while page loads.
        // Author: Ankush / Dhiraj
        string userIDTextField = "//input[@id=\'username\']";
        IEBrowser.WaitForObject(userIDTextField, 60000);
        // IEBrowser.WaitForObject("//BrowserApplication//BrowserWindow//input[@id='username']",30000)
        // (silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("More information").Exists())
        // If (silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("More information").Exists()) Then
        //     silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("More information").Click
        // End If
        // If (silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("CertificateErrorContinueLink").Exists()) Then
        //     silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("CertificateErrorContinueLink").Click
        // End If
        DynamicParentWait(silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("User No"));
    }
    
    public static void LaunchApplicationOld() {
        WriteConsoleLog("LaunchApplication started");
        Agent.Attach("iexplore.exe", "");
        // C:\Program Files\Internet Explorer\iexplore.exe
        Wait(2);
        //             inputdata="https://10.191.152.68:6004/"    '********************** Change this later
        BrowserBaseState IE = new BrowserBaseState(BrowserType.InternetExplorer, inputdata);
        BrowserApplication IEBrowser;
        // close any open browsers before launching new browser - IE
        try {
            List<BrowserApplication> Browsers;
            Browsers = Silktest.FindAll(Of, BrowserApplication)["/BrowserApplication"];
            BrowserApplication Browser;
            try {
                for (Browser : Browsers) {
                    //                     Dim x = Browser.Enabled
                    //                     If x = False Then 
                    //                         Call CloseApp()
                    //                     End If
                    Browser.SetActive();
                    Browser.CloseOtherTabs();
                    Browser.CloseSynchron(true);
                    // CloseProcess("WerFault.exe") 'closes IE Crash popup
                    Wait(1);
                }
                
                IEBrowser = IE.Execute();
                //                 IEBrowser.ClearCache(0)
                //                 IEBrowser.CloseSynchron(True)
                //                 IEBrowser = IE.Execute()
                //                 IEBrowser.Navigate(inputdata)
                //             Dim i = 0                
                //             While(i < 2)
                //                 Try
                //                     If silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomTextField("User No").Exists() Then
                //                         i = 5    'Exit if the user no field exists
                //                     End If                                
                //                 Catch ex1 As Exception
                //                     wait(1)
                //                 End Try
                //                 i = i + 1
                //             End While
                IEBrowser.Maximize();
            }
            catch (Exception ex1) {
                // in case of an exception trying to call the LaunchApplication Sub again by killing the iexplore.exe from task manager for a fresh launch
                MsgBox(ex1.Message);
                WriteConsoleLog(("LaunchApplication exception generated. " + ex1.Message));
                WriteConsoleLog("Killing iexplore.exe");
                // CloseProcess("iexplore.exe")
                //                 IEBrowser = Nothing
                //                 IE = Nothing
                WriteConsoleLog("LaunchApplication started again");
                Main.LaunchApplication();
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1            
            }
            
            WriteConsoleLog("LaunchApplication finished");
        }
        
    }
    
    static void TableInput() {
        // lookupColumn:lookUpData:performColumn:DomTextField:inputdata
        // If text field it will set value else Click on performColumn number        Specifically used for Cash deposit.
        // It will find the exact match for the test data in the table
        WriteConsoleLog("TableInput started");
        object bFlag = false;
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs;
                if (((inputdata.IndexOf("|") + 1) 
                            > 0)) {
                    tableArgs = inputdata.Split("|");
                }
                else {
                    tableArgs = inputdata.Split(":");
                }
                
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                // 'MsgBox(rowcount)
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    //                 MsgBox(lookUpColData)            
                    if ((lookUpColData == tableArgs[1])) {
                        bFlag = true;
                        TestObject performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2]));
                        switch (tableArgs[3].ToUpper()) {
                            case "DOMTEXTFIELD":
                                performObj.DomTextField.SetText(tableArgs[4]);
                                break; // TODO: Warning!!! Review that break works as 'Exit For' as it is inside another 'breakable' statement:Switch
                                break;
                            case "DOMLINK":
                                performObj.DomLink.Click;
                                break; // TODO: Warning!!! Review that break works as 'Exit For' as it is inside another 'breakable' statement:Switch
                                break;
                            case "DOMBUTTON":
                                performObj.DomButton.Click;
                                break; // TODO: Warning!!! Review that break works as 'Exit For' as it is inside another 'breakable' statement:Switch
                                break;
                            case "DOMELEMENT":
                                objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2])).Click;
                                break; // TODO: Warning!!! Review that break works as 'Exit For' as it is inside another 'breakable' statement:Switch
                                break;
                        }
                    }
                    
                }
                
                if ((bFlag == false)) {
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                    // recoveryFlag=1
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableInput finished");
    }
    
    public static void TableDoubleClick() {
        // Col:DOMELEMENT:Col:var
        // 0::DOMELEMENT:Var    
        WriteConsoleLog("TableDoubleClick started");
        object bFlag = false;
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                if ((tableArgs.Length > 6)) {
                    tempScriptID = scenarioID;
                    strVariable = tableArgs[3];
                    retrieved_Data = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                    tableArgs[1] = retrieved_Data;
                }
                
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    if ((lookUpColData == tableArgs[1])) {
                        bFlag = true;
                        TestObject performObj;
                        switch (tableArgs[2].ToUpper()) {
                            case "DOMLINK":
                                performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2]));
                                performObj.DomLink.DoubleClick;
                                break;
                            case "DOMBUTTON":
                                performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2]));
                                performObj.DomButton.DoubleClick;
                                break;
                            case "DOMELEMENT":
                                objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[0])).DoubleClick;
                                break; // TODO: Warning!!! Review that break works as 'Exit For' as it is inside another 'breakable' statement:Switch
                                break;
                        }
                    }
                    
                }
                
                if ((bFlag == false)) {
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                    // recoveryFlag=1
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableDoubleClick exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableDoubleClick finished");
    }
    
    public static void TableDoubleClickFromDB() {
        // lookupColumn:lookUpData:performColumn:DomTextField:inputdata
        // 0:Var:0:DomElemnt:    
        WriteConsoleLog("TableDoubleClickFromDB started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            object bFlag = false;
            try {
                Array tableArgs = inputdata.Split(":");
                if (((tableArgs[1].IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = tableArgs[1].Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = tableArgs[1];
                }
                
                retrieved_Data = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    retrieved_Data = retrieved_Data.Trim();
                    object sText;
                    if ((lookUpColData == retrieved_Data)) {
                        bFlag = true;
                        TestObject performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2]));
                        switch (tableArgs[3].ToUpper()) {
                            case "DOMTEXTFIELD":
                                performObj.DomTextField.SetText(tableArgs[4]);
                                sText = performObj.DomTextField.Text;
                                if ((sText != tableArgs[4])) {
                                    resultFlag = "Fail";
                                }
                                
                                break;
                            case "DOMLINK":
                                performObj.DomLink.DoubleClick;
                                break;
                            case "DOMBUTTON":
                                performObj.DomButton.DoubleClick;
                                break;
                            case "DOMELEMENT":
                                objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2])).DoubleClick;
                                break; // TODO: Warning!!! Review that break works as 'Exit For' as it is inside another 'breakable' statement:Switch
                                break;
                        }
                    }
                    
                }
                
                if ((bFlag == false)) {
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                    // recoveryFlag=1
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableDoubleClickFromDB exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableDoubleClickFromDB finished");
    }
    
    public static void RetStore() {
        WriteConsoleLog("RetStore started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                switch (className) {
                    case "DomTextField":
                        DynamicObjectWait();
                        fieldData = objValue.DomTextField(fieldName).Text;
                        break;
                    case "DomCheckBox":
                        DynamicObjectWait();
                        fieldData = objValue.DomCheckBox(fieldName).Text;
                        break;
                    case "DomListBox":
                        DynamicObjectWait();
                        fieldData = objValue.DomListBox(fieldName).Text;
                        break;
                    case "DomElement":
                        DynamicObjectWait();
                        if ((fieldName == "Message")) {
                            string strMessage;
                            int exitLoop = 0;
                            strMessage = "";
                            while ((exitLoop < 50)) {
                                WriteConsoleLog(("Waiting for strMessage " 
                                                + (exitLoop + (" " + strMessage))));
                                strMessage = objValue.DomElement(fieldName).Text;
                                if ((((strMessage.IndexOf("Processing...") + 1) 
                                            == 0) 
                                            && (strMessage.Trim() != ""))) {
                                    exitLoop = 51;
                                }
                                else {
                                    exitLoop = (exitLoop + 1);
                                    Wait(1);
                                }
                                
                            }
                            
                        }
                        
                        fieldData = objValue.DomElement(fieldName).Text;
                        break;
                    case "DomRadioButton":
                        DynamicObjectWait();
                        fieldData = objValue.DomRadioButton(fieldName).Text;
                        break;
                    case "Label":
                        DynamicObjectWait();
                        fieldData = objValue.Label(fieldName).Text;
                        break;
                }
                fieldData = fieldData.Trim();
                Main.WriteToLookupFile();
                actualMessage = "Value stored in lookup file";
            }
            catch (Exception ex) {
                WriteConsoleLog(("RetStore exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RetStore finished");
    }
    
    public static void WriteToLookupFile() {
        WriteConsoleLog("WriteToLookupFile started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            object l;
            try {
                objLookupfile = CreateObject("Excel.Application");
                objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite, 1);
                lineNumber = 0;
                while (!objLookupFile.AtEndOfStream) {
                    lineNumber = (lineNumber + 1);
                    retFileData(lineNumber) = objLookupFile.ReadLine;
                }
                
                objLookupFile.Close;
                objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite, 2);
                for (l = 1; (l <= lineNumber); l++) {
                    if (((retFileData(l).IndexOf((scenarioID + ("," + inputData))) + 1) 
                                == 0)) {
                        objLookupFile.WriteLine(retFileData(l));
                    }
                    
                }
                
                if (((fieldData.IndexOf("\'") + 1) 
                            == 1)) {
                    fieldData = Replace(fieldData, "\'", "", 1, 1);
                }
                
                fieldData = fieldData.Replace(",", "");
                fieldData = fieldData.Trim();
                object fieldData_new = ("\'" + fieldData);
                objLookupFile.WriteLine((scenarioID + ("," 
                                + (inputData + ("," + fieldData_new)))));
                objLookupFile.Close;
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                WriteConsoleLog(("WriteToLookupFile exception generated. " + ex.Message));
                // recoveryFlag=1
                resultFlag = "Fail";
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("WriteToLookupFile finished");
    }
    
    public static void RetInput() {
        WriteConsoleLog("RetInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                DynamicObjectWait();
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                object sText;
                // MsgBox(className)
                switch (className) {
                    case "DomTextField":
                        try {
                            objValue.DomTextField(fieldName).SetText(retrieved_Data);
                            // msgbox(retrieved_Data)
                        }
                        catch (System.Exception End) {
                            try {
                                if (!(retrieved_Data == "")) {
                                    objValue.DomTextField(fieldName).SetText(retrieved_Data);
                                    sText = objValue.DomTextField(fieldName).Text;
                                    if ((sText != retrieved_Data)) {
                                        resultFlag = "Fail";
                                    }
                                    
                                }
                                
                                "DomListBox";
                                inputdata = retrieved_Data;
                                // uncommented this line to enter data in a listbox from lookup tab
                                Inputlist();
                                "DomElement";
                                inputdata = retrieved_Data;
                                // msgbox(retrieved_Data)
                                //                         Try
                                //                             objValue.DomElement(fieldName).Click
                                //                             objValue.DomElement(fieldName).SetProperty("value","")
                                //                             objValue.DomElement(fieldName).TypeKeys(retrieved_Data)
                                //                             msgbox(fieldName)
                                //                         Catch 
                                //                         End Try
                                if (!(retrieved_Data == "")) {
                                    objValue.DomElement(fieldName).SetProperty("value", "");
                                    objValue.DomElement(fieldName).TypeKeys(retrieved_Data);
                                    sText = objValue.DomElement(fieldName).Value;
                                    // msgbox(sText)
                                    // msgbox(retrieved_Data)
                                }
                                
                                try {
                                    if ((sText != retrieved_Data)) {
                                        resultFlag = "Fail";
                                    }
                                    
                                }
                                catch (System.Exception If) {
                                    objValue.DomElement(fieldName).GetDomAttribute("title") = "Please enter the highest amount";
                                    objValue.DomElement(fieldName).TypeKeys(inputdata);
                                    DateInput();
                                }
                                
                            }
                            
                            //                         If (objValue.DomElement(fieldName).GetDomAttribute("title")) = "Please enter the highest amount"
                            //                                  objValue.DomElement(fieldName).TypeKeys(inputdata)
                            //                            Else
                            //                              Call DateInput()
                            //                         End If
                            "DomButton";
                            DateInput();
                            resultFlag = "Pass";
                        }
                        catch (Exception ex) {
                            WriteConsoleLog(("RetInput exception generated. " + ex.Message));
                            actualMessage = Err.Description;
                            SetRecoveryAndResultFlag();
                            // recoveryFlag=1
                            Main.WriteToOutputFile();
                        }
                        
                        break;
                }
            }
            
            WriteConsoleLog("RetInput finished");
        }
        
    }
    
    static void SaveMonth() {
        WriteConsoleLog("RetInput finished");
    }
    
    public static void GetRetirevData(void lookupFilePath, void lookupFileName, void tempScriptID, void strVariable) {
        WriteConsoleLog("GetRetirevData started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            string strConnStrings = ("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" 
                        + (lookupFilePath + ";Extended Properties=\'text;HDR=Yes;FMT=Delimited(,)\';"));
            OleDbConnection Datacon = new OleDbConnection(strConnStrings);
            try {
                Datacon.Open();
                string Query = ("SELECT * FROM [" 
                            + (lookupFileName + ("] where Scenario_Id =\'" 
                            + (tempScriptID + ("\' and Variable =\'" 
                            + (strVariable + "\'"))))));
                OleDbCommand cmd = new OleDbCommand(Query, Datacon);
                OleDbDataAdapter da = new OleDbDataAdapter(cmd);
                da.SelectCommand = cmd;
                DataSet ds = new DataSet();
                object fild;
                da.Fill(ds);
                fild = ds.Tables[0].Rows.Count;
                string lookupfileval;
                for (i = 0; (i 
                            <= (fild - 1)); i++) {
                    lookupfileval = ds.Tables[0].Rows[i].Item[2].ToString;
                    if (((lookupfileval.IndexOf("\'") + 1) 
                                == 1)) {
                        lookupfileval = Replace(lookupfileval, "\'", "", 1, 1);
                    }
                    
                }
                
                // msgbox(fild.Rows(0).Item(2).ToString)
                da.Dispose;
                if ((((tempScriptID.IndexOf("IR-75273") + 1) 
                            > 0) 
                            && (strVariable == "Month1"))) {
                    lookupfileval = (lookupfileval + " ");
                }
                else if (((tempScriptID == "IR-75735-002") 
                            && (strVariable == "Month1"))) {
                    lookupfileval = (lookupfileval + " ");
                }
                else {
                    lookupfileval = lookupfileval.Trim();
                }
                
                if ((lookupfileval == "")) {
                    return null;
                }
                
                return lookupfileval;
            }
            catch (Exception ex) {
                WriteConsoleLog(("GetRetirevData exception generated. " + ex.Message));
                return null;
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
            }
            finally {
                Datacon.Close();
            }
            
        }
        
        WriteConsoleLog("GetRetirevData finished");
    }
    
    public static void VerifywithDB() {
        WriteConsoleLog("VerifywithDB started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                if (((inputdata.IndexOf("||") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split("||");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = Main.GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                // msgbox (retrieved_Data)
                // msgbox (inputdata)
                if ((retrieved_Data == "")) {
                    resultFlag = "Fail";
                    Main.WriteToOutputFile();
                }
                
                switch (className) {
                    case "DomTextField":
                        DynamicObjectWait();
                        fieldData = objValue.DomTextField(fieldName).Text;
                        break;
                    case "DomCheckBox":
                        DynamicObjectWait();
                        fieldData = objValue.DomCheckBox(fieldName).Text;
                        break;
                    case "DomListBox":
                        DynamicObjectWait();
                        fieldData = objValue.DomListBox(fieldName).Text;
                        break;
                    case "DomElement":
                        DynamicObjectWait();
                        fieldData = objValue.DomElement(fieldName).Text;
                        break;
                    case "DomRadioButton":
                        DynamicObjectWait();
                        fieldData = objValue.DomRadioButton(fieldName).Text;
                        break;
                }
                fieldData = fieldData.Replace(",", "");
                if (((fieldData.IndexOf(retrieved_Data) + 1) 
                            == 0)) {
                    resultFlag = "Fail";
                }
                
                actualMessage = fieldData;
                stepExpectedResult = retrieved_Data;
            }
            catch (Exception ex) {
                WriteConsoleLog(("VerifywithDB exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                Main.WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("VerifywithDB finished");
    }
}
 public final void SplitAndStoreInDB() {
        WriteConsoleLog("SplitAndStoreInDB started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object sFlag = false;
                object sStroreValue;
                inputdataSplit = inputdata.Split(";");
                strVariable = inputdataSplit(0);
                splitBy = inputdataSplit(1);
                varToStore = inputdataSplit(2);
                positionToStore = inputdataSplit(3);
                objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite, 1);
                lineNumber = 0;
                object retFil;
                object bolFlag = 0;
                // msgbox(strVariable)
                while (!objLookupFile.AtEndOfStream) {
                    // While Not bolFlag =1
                    lineNumber = (lineNumber + 1);
                    retFileData(lineNumber) = objLookupFile.ReadLine;
                    sStroreValue = retFileData(lineNumber).Split(",");
                    // msgBox(sStroreValue)
                    if ((((retFileData(lineNumber).IndexOf(scenarioID) + 1) 
                                > 0) 
                                && ((sStroreValue[1].Substring(0, strVariable.Length).IndexOf(strVariable) + 1) 
                                != 0))) {
                        if ((sStroreValue[1] == strVariable)) {
                            // msgbox(strVariable)
                            splString = retFileData(lineNumber).Split(",");
                            retrieved_Data = splString(2);
                            // sFlag = True
                        }
                        
                    }
                    
                    // If sFlag = True Then 
                    //     Exit While
                    // End If    
                }
                
                objLookupFile.Close;
                dataSplit = retrieved_Data.Split(splitBy);
                splValueToStore = dataSplit(positionToStore);
                fieldData = splValueToStore;
                inputdata = varToStore;
                objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite, 2);
                for (l = 1; (l <= lineNumber); l++) {
                    if (((retFileData(l).IndexOf((scenarioID + ("," + varToStore))) + 1) 
                                == 0)) {
                        objLookupFile.WriteLine(retFileData(l));
                    }
                    
                }
                
                objLookupFile.WriteLine((scenarioID + ("," 
                                + (inputdata + ("," + fieldData)))));
                objLookupFile.Close;
                WriteToLookupFile();
            }
            catch (Exception ex) {
                WriteConsoleLog(("SplitAndStoreInDB exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("SplitAndStoreInDB finished");
    }
    
    //  Var1 ; Var2 ; Var to store
    public final void ConcatenateaAndStoreInDB() {
        WriteConsoleLog("ConcatenateaAndStoreInDB started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object strVariable1;
                object strVariable2;
                object varToStore;
                inputdataSplit = inputdata.Split(";");
                strVariable1 = inputdataSplit(0);
                strVariable2 = inputdataSplit(1);
                varToStore = inputdataSplit(2);
                object retrieved_Data1 = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable1);
                // Dim retrieved_Data1 = retrieveValueExtractorctor.Tables(0).Rows(0).Item(2).ToString    
                object retrieved_Data2 = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable2);
                // Dim retrieved_Data2 = retrieveValueExtractorctor.Tables(0).Rows(0).Item(2).ToString    
                object conValueToStore = (retrieved_Data1 + retrieved_Data2);
                inputData = varToStore;
                fieldData = conValueToStore;
                WriteToLookupFile();
            }
            catch (Exception ex) {
                WriteConsoleLog(("ConcatenateaAndStoreInDB exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ConcatenateaAndStoreInDB finished");
    }
    
    public final void Wait() {
        // INPUT DATA IN SECONDS
        WriteConsoleLog(("Waiting for " 
                        + (inputdata + "s")));
        object initTime;
        Now;
        object setTime = DateAdd("s", inputdata, Now);
        while ((setTime > initTime)) {
            Now;
            //             WriteConsoleLog("Sleeping")
        }
        
        WriteConsoleLog("Wait finished");
    }
    
    public final void Wait(void inputdata) {
        WriteConsoleLog(("Waiting for " 
                        + (inputdata + "s")));
        object initTime;
        Now;
        object setTime = DateAdd("s", inputdata, Now);
        while ((setTime > initTime)) {
            //             WriteConsoleLog("Sleeping")
            Now;
        }
        
        WriteConsoleLog("Wait with inputdata finished");
    }
    
    public final void TableInputFromDB() {
        //  Col:[script-id];variable:0:obj type: value
        WriteConsoleLog("TableInputFromDB started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            object bFlag = false;
            try {
                Array tableArgs = inputdata.Split(":");
                if (((tableArgs[1].IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = tableArgs[1].Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = tableArgs[1];
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                DynamicObjectWait();
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    retrieved_Data = retrieved_Data.Trim();
                    object sText;
                    //             msgbox (lookUpColData)
                    //             msgbox (retrieved_Data)
                    if ((lookUpColData == retrieved_Data)) {
                        bFlag = true;
                        TestObject performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2]));
                        switch (tableArgs[3].ToUpper()) {
                            case "DOMTEXTFIELD":
                                performObj.DomTextField.SetText(tableArgs[4]);
                                sText = performObj.DomTextField.Text;
                                if ((sText != tableArgs[4])) {
                                    resultFlag = "Fail";
                                }
                                
                                break;
                            case "DOMLINK":
                                performObj.DomLink.Click;
                                break;
                            case "DOMBUTTON":
                                performObj.DomButton.Click;
                                break;
                            case "DOMELEMENT":
                                objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2])).Click;
                                break;
                        }
                    }
                    
                }
                
                if ((bFlag == false)) {
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                    // recoveryFlag=1
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableInputFromDB exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableInputFromDB finished");
    }
    
    public final void PressKey() {
        WriteConsoleLog("PressKey started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                DynamicObjectWait();
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, inputdataSplit(1));
                    inputdata = retrieved_Data;
                }
                
                switch (className) {
                    case "DomTextField":
                        objValue.DomTextField(fieldName).Click();
                        if (((inputdata.ToUpper() == "TAB") 
                                    || (inputdata.ToUpper() == "ENTER"))) {
                            objValue.DomTextField(fieldName).PressKeys(("<" 
                                            + (inputdata + ">")));
                        }
                        else {
                            objValue.DomTextField(fieldName).PressKeys(inputdata);
                            objValue.DomTextField(fieldName).PressKeys("<TAB>");
                        }
                        
                        Wait(1);
                        break;
                    case "DomButton":
                        if (((inputdata.ToUpper() == "TAB") 
                                    || (inputdata.ToUpper() == "ENTER"))) {
                            objValue.DomButton(fieldName).PressKeys(("<" 
                                            + (inputdata + ">")));
                        }
                        else {
                            objValue.DomButton(fieldName).PressKeys(inputdata);
                        }
                        
                        break;
                    case "DomListBox":
                        if (((inputdata.ToUpper() == "TAB") 
                                    || (inputdata.ToUpper() == "ENTER"))) {
                            objValue.DomListBox(fieldName).PressKeys(("<" 
                                            + (inputdata + ">")));
                        }
                        else {
                            objValue.DomListBox(fieldName).Click();
                            objValue.DomListBox(fieldName).PressKeys(inputdata);
                            objValue.DomListBox(fieldName).PressKeys("<TAB>");
                        }
                        
                        break;
                    case "DomElement":
                        if (((inputdata.ToUpper() == "TAB") 
                                    || (inputdata.ToUpper() == "ENTER"))) {
                            objValue.DomElement(fieldName).PressKeys(("<" 
                                            + (inputdata + ">")));
                        }
                        else {
                            objValue.DomElement(fieldName).PressKeys(inputdata);
                        }
                        
                        break;
                    case "DomLink":
                        objValue.DomLink(fieldName).PressKeys(("<" 
                                        + (inputdata + ">")));
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("PressKey exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("PressKey finished");
    }
    
    public final void ARITHMETIC() {
        // Script_Id;var1;operator;var2;Result
        // SCR-TDS-009;IntPaidAmt;MUL;Perctng;ExpTaxDed
        // SCR-TDS-009;IntPaidAmt;MUL;0.1;ExpTaxDed
        WriteConsoleLog("ARITHMETIC started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                inputdataSplit = inputdata.Split(";");
                tempScriptID = inputdataSplit(0);
                v_Arithmetic_Value_1 = inputdataSplit(1);
                v_Arithmetic_Operator = inputdataSplit(2).ToUpper();
                v_Arithmetic_Value_2 = inputdataSplit(3);
                retrieved_Data_1 = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, v_Arithmetic_Value_1);
                retrieved_Data_1 = double.Parse(retrieved_Data_1);
                retrieved_Data_2 = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, v_Arithmetic_Value_2);
                // msgbox(retrieved_Data_2)
                if ((retrieved_Data_2 == null)) {
                    retrieved_Data_2 = inputdataSplit(3);
                }
                
                retrieved_Data_2 = double.Parse(retrieved_Data_2);
                switch (v_Arithmetic_Operator) {
                    case "ADD":
                        v_Calculated_Value = (retrieved_Data_1 + retrieved_Data_2);
                        // msgBox(v_Calculated_Value)
                        break;
                    case "SUB":
                        v_Calculated_Value = (retrieved_Data_1 - retrieved_Data_2);
                        break;
                    case "MUL":
                        v_Calculated_Value = (retrieved_Data_1 * retrieved_Data_2);
                        // msgBox(v_Calculated_Value)
                        break;
                    case "DIV":
                        v_Calculated_Value = (retrieved_Data_1 / retrieved_Data_2);
                        break;
                    case "ROUND":
                        object val1;
                        val1 = retrieved_Data_1;
                        object val2;
                        val2 = int.Parse(retrieved_Data_2);
                        object val3 = Math.Round(Val1, Val2);
                        v_Calculated_Value = val3;
                        // msgbox(v_Calculated_Value)        
                        break;
                    case "ROUND1":
                        v_Calculated_Value = math.floor(retrieved_Data_1);
                        break;
                }
                fieldData = v_Calculated_Value;
                inputData = inputdataSplit(4);
                // msgbox(inputData)
                WriteToLookupFile();
                actualMessage = "Value stored in lookup file";
            }
            catch (Exception ex) {
                WriteConsoleLog(("ARITHMETIC exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ARITHMETIC finished");
    }
    
    public final void TableRetrieve() {
        // Col no:Data:Col no:ObjectType:Variable
        // Retrieves the data from a table and stores in lookup file
        // 0:INR:2:DOMELEMENT:CurBal
        // 5:1000.00 DR:3:DOMELEMENT:JrnlNo
        WriteConsoleLog("TableRetrieve started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                // MsgBox(rowcount)
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    // ColName:??:ColNum:ElementType:VariableName
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    // Dim performObj As TestObject = objValue.DomTable(fieldName).GetCell(iterator,CInt(tableArgs(0)))
                    if ((lookUpColData == tableArgs[1])) {
                        switch (tableArgs[3].ToUpper()) {
                            case "DOMTEXTFIELD":
                                lookUpRowData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[2]));
                                break;
                            case "DOMLINK":
                                lookUpRowData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[2]));
                                break;
                            case "DOMBUTTON":
                                lookUpRowData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[2]));
                                break;
                            case "DOMELEMENT":
                                lookUpRowData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[2]));
                                break;
                            case "DOMTABLE":
                                lookUpRowData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[2]));
                                break;
                        }
                        break;
                    }
                    
                }
                
                fieldData = lookUpRowData.Replace(",", "");
                inputData = tableArgs[4];
                WriteToLookupFile();
                actualMessage = "Value stored in lookup file";
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableRetrieve exception generated. " + ex.Message));
                SetRecoveryAndResultFlag();
                actualMessage = Err.Description;
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableRetrieve finished");
    }
    
    public final void VariableCompare() {
        WriteConsoleLog("VariableCompare started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                inputdataSplit = inputdata.Split(";");
                scenarioID = inputdataSplit(0);
                object strVariable1 = inputdataSplit(1);
                object strVariable2 = inputdataSplit(2);
                // msgbox (strVariable1)
                // msgbox (strVariable2)
                object retrieved_Data1;
                object retrieved_Data2;
                //             objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite,1)
                //             lineNumber=0
                //         
                //             While Not objLookupFile.AtEndOfStream
                //                 lineNumber=lineNumber+1            
                //                 retFileData(lineNumber)=objLookupFile.ReadLine        
                //                 
                //                 If Instr(retFileData(lineNumber),v_Arithmetic_Value_1)>0 Then
                //                     splString =Split(retFileData(lineNumber),",")
                //                     retrieved_Data_1=(splString(2))
                //                 End If
                //                 If Instr(retFileData(lineNumber),v_Arithmetic_Value_2)>0 Then
                //                     splString =Split(retFileData(lineNumber),",")
                //                     retrieved_Data_2=(splString(2))
                //                 End If
                //             End While     
                retrieved_Data1 = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable1);
                retrieved_Data2 = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable2);
                if ((retrieved_Data2 == "")) {
                    retrieved_Data2 = strVariable2;
                }
                
                // msgbox (retrieved_Data1)
                // msgbox (retrieved_Data2)
                if (((retrieved_Data1.IndexOf(retrieved_Data2) + 1) 
                            == 0)) {
                    resultFlag = "Fail";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("VariableCompare exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("VariableCompare finished");
    }
    
    public final void TableRowInputDB() {
        // Col no:scenarioID:strVariable2:ObjectType:Variable
        // 1::AccountNumber:DOMELEMENT
        // 1::AccntNo:DOMELEMENT
        WriteConsoleLog("TableRowInputDB started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                object strVariable2;
                scenarioID = tableArgs[1];
                strVariable2 = tableArgs[2];
                object retrieved_Data2 = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable2);
                // Dim retrieved_Data2 = retrieveValueExtractorctor.Tables(0).Rows(0).Item(2).ToString    
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    if (((lookUpColData.IndexOf("END OF TXN") + 1) 
                                > 0)) {
                        return;
                    }
                    
                    if (((lookUpColData.IndexOf(retrieved_Data2) + 1) 
                                > 0)) {
                        TestObject performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[0]));
                        switch (tableArgs[3].ToUpper()) {
                            case "DOMTEXTFIELD":
                                performObj.DomTextField.SetText(tableArgs[0]);
                                resultFlag = "Pass";
                                break;
                            case "DOMLINK":
                                performObj.DomLink.Click;
                                resultFlag = "Pass";
                                break;
                            case "DOMBUTTON":
                                performObj.DomButton.Click;
                                resultFlag = "Pass";
                                break;
                            case "DOMELEMENT":
                                objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[0])).Click;
                                resultFlag = "Pass";
                                break;
                        }
                        break;
                    }
                    else {
                        resultFlag = "Fail";
                    }
                    
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableRowInputDB exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableRowInputDB finished");
    }
    
    public final void TableRowRetStore() {
        // 0:ChqTbl:DOMELEMENT:
        WriteConsoleLog("TableRowRetStore started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    if ((tableArgs[0] == "")) {
                        tableArgs[0] = (rowCount - 1);
                    }
                    
                    if ((iterator == tableArgs[0])) {
                        switch (tableArgs[2]) {
                            case "DomTextField":
                                fieldData = objValue.DomTextField(fieldName).Text;
                                break;
                            case "DomCheckBox":
                                fieldData = objValue.DomCheckBox(fieldName).Text;
                                break;
                            case "DomListBox":
                                fieldData = objValue.DomListBox(fieldName).Text;
                                break;
                            case "DomElement":
                                object fieldData1 = objValue.DomElement(fieldName).Text;
                                fieldData = fieldData1.Replace(",", "");
                                break;
                            case "DomRadioButton":
                                fieldData = objValue.DomRadioButton(fieldName).Text;
                                break;
                        }
                        inputData = tableArgs[1];
                        // msgbox(inputData)
                        WriteToLookupFile();
                        actualMessage = "Value stored in lookup file";
                        break;
                    }
                    
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableRowRetStore exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableRowRetStore finished");
    }
    
    public final void Click() {
        // //Created Date: 9-Feb-2015
        // //Drecription:     For click on Browser object only
        // //Parameters:  No parameter    
        WriteConsoleLog("Click started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                switch (className) {
                    case "DomTextField":
                        objValue.DomTextField(fieldName).Click;
                        break;
                    case "DomButton":
                        objValue.DomButton(fieldName).Click;
                        break;
                    case "DomCheckBox":
                        objValue.DomCheckBox(fieldName).Click;
                        break;
                    case "DomListBox":
                        objValue.DomListBox(fieldName).Click;
                        break;
                    case "DomElement":
                        objValue.DomElement(fieldName).Click;
                        break;
                    case "DomLink":
                        objValue.DomLink(fieldName).Click;
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("Click exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("Click finished");
    }
    
    public final void TableRowInput() {
        // 3:31/03/2014:3:DOMELEMENT
        WriteConsoleLog("TableRowInput started");
        // It will INSTR to for matching the test data in the table and not the exact match
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                DynamicParentWait(objValue.DomTable(fieldName));
                // prashant
                object exitLoop = true;
                while (exitLoop) {
                    object strMessage = silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("Message").GetText();
                    if (((strMessage.IndexOf("Processing") + 1) 
                                == 0)) {
                        exitLoop = false;
                        Wait(1);
                    }
                    
                }
                
                Array tableArgs = inputdata.Split(":");
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                int iterator;
                object bolFlag = 0;
                objValue.WaitForObject(fieldName);
                //             objValue.DomTable(fieldName).Highlight("RED","CYAN")
                //             objValue.DomTable(fieldName).Unhighlight
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[0]));
                    //                 msgbox(objValue.DomTable(fieldName).GetCellText(iterator,9))
                    //                 MsgBox(lookUpColData)
                    //                 MsgBox(tableArgs(1))
                    if (((lookUpColData.IndexOf("END OF TXN") + 1) 
                                > 0)) {
                        return;
                    }
                    
                    if (((lookUpColData.IndexOf(tableArgs[1]) + 1) 
                                > 0)) {
                        // lookUpColData = tableArgs(1) Then 'Instr(lookUpColData,tableArgs(1)) > 0  Then
                        TestObject performObj = objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2]));
                        switch (tableArgs[3].ToUpper()) {
                            case "DOMTEXTFIELD":
                                performObj.DomTextField.SetText(tableArgs[4]);
                                break;
                            case "DOMLINK":
                                performObj.DomLink.Click;
                                break;
                            case "DOMBUTTON":
                                performObj.DomButton.Click;
                                break;
                            case "DOMELEMENT":
                                objValue.DomTable(fieldName).GetCell(iterator, int.Parse(tableArgs[2])).Click;
                                break;
                        }
                        bolFlag = 1;
                        break;
                        //                 Else
                        //                         'bolFlag =1 
                        //                     resultFlag ="Fail"
                    }
                    
                }
                
                if ((bolFlag == 0)) {
                    resultFlag = "Fail";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableRowInput exception generated. " + ex.Message));
                // MsgBox("Exception")
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableRowInput finished");
    }
    
    public final void IncrementBy() {
        WriteConsoleLog("IncrementBy started");
        //  Increments the Lookup variable with a hardcoded value
        // Scenario id:RetriveVariableName:2:RestoreVariableName    
        //         inputdataSplit=Split(inputdata,";")
        //          v_Arithmetic_Value_1 =inputdataSplit(0)
        //          v_Arithmetic_Value_2 =inputdataSplit(1)
        //          v_Arithmetic_Final_Value  =inputdataSplit(2) 
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                object strVariable2;
                scenarioID = tableArgs[0];
                // msgbox(scenarioID)
                strVariable2 = tableArgs[1];
                // msgbox(strVariable2)
                v_Arithmetic_Value_2 = tableArgs[2];
                // msgbox(v_Arithmetic_Value_2)
                v_Arithmetic_Final_Value = tableArgs[3];
                // msgbox(v_Arithmetic_Final_Value)
                object retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable2);
                // Call WriteToLookupFile()
                //         objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite,1)
                //         lineNumber=0
                //         
                //         While Not objLookupFile.AtEndOfStream
                //             lineNumber=lineNumber+1            
                //             retFileData(lineNumber)=objLookupFile.ReadLine
                //             Msgbox(retFileData(lineNumber))
                //         
                //             If Instr(retFileData(lineNumber),v_Arithmetic_Value_1)>0 Then      
                //                   
                //                Dim splString=Split(retFileData(lineNumber),",")            
                //                 retrieved_Data_1 =CDbl(splString(2))
                //                 Dim Temp2 =(splString(2))
                //                 retrieved_Data_1 = Replace(Temp2,"'","") 
                //                 msgbox(retrieved_Data_1)
                //                                 
                //             End If
                // End While                 
                fieldData = (int.Parse(retrieved_Data) + v_Arithmetic_Value_2);
                inputdata = v_Arithmetic_Final_Value;
                // msgbox(fieldData)
                WriteToLookupFile();
                // msgbox(v_Calculated_Value)
                //         objLookupFile.Close
                //     
                //         objLookupFile = objFSO.OpenTextFile(lookupFilePathForWrite,2)                    
                //                 
                //         For l=1 To lineNumber
                //             If Instr(retFileData(l),scenarioID & "," & v_Arithmetic_Final_Value)=0 Then 
                //                 objLookupFile.WriteLine(retFileData(l))            
                //             End If
                //         Next
                //         objLookupFile.WriteLine(scenarioID & "," & v_Arithmetic_Final_Value & "," & v_Calculated_Value)            
                //         objLookupFile.Close
            }
            catch (Exception ex) {
                WriteConsoleLog(("IncrementBy exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("IncrementBy finished");
    }
    
    public final void DecrementBy() {
        //  Decrements the Lookup variable with a hardcoded value
        WriteConsoleLog("DecrementBy started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                object strVariable2;
                scenarioID = tableArgs[0];
                strVariable2 = tableArgs[1];
                v_Arithmetic_Value_2 = tableArgs[2];
                v_Arithmetic_Final_Value = tableArgs[3];
                object retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable2);
                fieldData = (int.Parse(retrieved_Data) - v_Arithmetic_Value_2);
                inputdata = v_Arithmetic_Final_Value;
                // msgbox(fieldData)
                WriteToLookupFile();
            }
            catch (Exception ex) {
                WriteConsoleLog(("DecrementBy exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("DecrementBy finished");
    }
    
    public final void OpenNewTab() {
        WriteConsoleLog("OpenNewTab started");
        // Agent.Attach("iexplore.exe", "")
        // System.Threading.Thread.Sleep(2000)
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            BrowserBaseState IE = new BrowserBaseState(BrowserType.InternetExplorer, inputdata);
            // Dim IEBrowser As BrowserApplication
            //             inputdata="https://10.191.152.68:6004/"
            // close any open browsers before launching new browser - IE
            try {
                List<BrowserApplication> Browsers;
                Browsers = Silktest.FindAll(Of, BrowserApplication)["/BrowserApplication"];
                BrowserApplication Browser;
                for (Browser : Browsers) {
                    BrowserWindow test;
                    test = Browser.GetActiveTab();
                    // Dim str As String
                    test.GetUrl();
                    if ((test.GetUrl().IndexOf("http://10.0.14.205:6001/") + 1)) {
                        Browser.OpenTab(inputdata);
                    }
                    else if ((test.GetUrl().IndexOf("https://10.189.15.177:6004/") + 1)) {
                        // If instr (test.GetUrl() , "https://10.189.15.203:6004/") Then
                        Browser.OpenTab(inputdata);
                    }
                    else if ((test.GetUrl().IndexOf("http://10.0.127.6:6004/") + 1)) {
                        Browser.OpenTab(inputdata);
                    }
                    else if ((test.GetUrl().IndexOf("https://10.191.152.68:6004/") + 1)) {
                        Browser.OpenTab(inputdata);
                    }
                    else if ((test.GetUrl().IndexOf("https://10.189.15.177:6061/") + 1)) {
                        Browser.OpenTab(inputdata);
                    }
                    else if ((test.GetUrl().IndexOf("http://10.189.15.125:7004/RestJspClient/TCP.jsp") + 1)) {
                        Browser.OpenTab(inputdata);
                    }
                    else if ((test.GetUrl().IndexOf("https://10.189.15.177:6067/") + 1)) {
                        Browser.OpenTab(inputdata);
                    }
                    else {
                        Browser.OpenTab(inputdata);
                    }
                    
                    // Date: 11.03.2022
                    // Error Desc: UserID Text Field unable to identify while New tab page load is in progress.
                    // Change Desc: Waiting for UserID Text Field while new tab page loads.
                    // Author: Ankush / Dhiraj
                    string userIDTextField = "//input[@id=\'username\']";
                    Browser.WaitForObject(userIDTextField, 60000);
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("OpenNewTab exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
            // IEBrowser = IE.Execute()
        }
        
        WriteConsoleLog("OpenNewTab finished");
    }
    
    public final void TableRowRetrieve() {
        // (When column value is not unique) 
        // row,column,domtype,variable
        WriteConsoleLog("TableRowRetrieve started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                DynamicObjectWait();
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                int iterator;
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    if ((iterator == tableArgs[0])) {
                        fieldData = objValue.DomTable(fieldName).GetCellText(iterator, int.Parse(tableArgs[1]));
                        // msgbox(fieldData)
                        break;
                    }
                    
                }
                
                // Msgbox(fieldData)                
                inputData = tableArgs[2];
                WriteToLookupFile();
                actualMessage = "Value stored in lookup file";
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableRowRetrieve exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableRowRetrieve finished");
    }
    
    public final void CloseTab() {
        WriteConsoleLog("CloseTab started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            BrowserBaseState IE = new BrowserBaseState(BrowserType.InternetExplorer, inputdata);
            // close any open browsers before launching new browser - IE
            try {
                List<BrowserApplication> Browsers;
                Browsers = Silktest.FindAll(Of, BrowserApplication)["//BrowserApplication"];
                BrowserApplication Browser;
                for (Browser : Browsers) {
                    if (Browser.IsActive()) {
                        Browser.CloseTab;
                        break;
                    }
                    
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("CloseTab exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
            // IEBrowser = IE.Execute()
        }
        
        WriteConsoleLog("CloseTab finished");
    }
    
    public final void RetrieveTableRow() {
        WriteConsoleLog("RetrieveTableRow started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(":");
                tempScriptID = tableArgs[0];
                strVariable = tableArgs[1];
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                // retrieved_Data = retrieveValueExtractorctor.Tables(0).Rows(0).Item(2).ToString
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                object colCount = objValue.DomTable(fieldName).GetColumnCount();
                int itr_row;
                int itr_col;
                for (itr_row = 0; (itr_row 
                            <= (rowcount - 1)); itr_row++) {
                    for (itr_col = 0; (itr_col 
                                <= (colCount - 1)); itr_col++) {
                        object lookUpColData = objValue.DomTable(fieldName).GetCellText(itr_row, itr_col);
                        // Dim performObj As TestObject = objValue.DomTable(fieldName).GetCell(iterator,CInt(tableArgs(0)))
                        if ((lookUpColData == retrieved_Data)) {
                            fieldData = itr_row;
                            break;
                        }
                        
                    }
                    
                }
                
                inputData = tableArgs[2];
                WriteToLookupFile();
                actualMessage = "Value stored in lookup file";
            }
            catch (Exception ex) {
                WriteConsoleLog(("RetrieveTableRow exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RetrieveTableRow finished");
    }
    
    public final void ConcatenateandInput() {
        WriteConsoleLog("ConcatenateandInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object data;
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    data = inputdataSplit(0);
                    concateData = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                switch (className) {
                    case "DomTextField":
                        object retrievedData_new = (concateData + data);
                        objValue.DomTextField(fieldName).SetText(retrievedData_new);
                        break;
                    case "DomListBox":
                        object retrievedData_new = (concateData + data);
                        objValue.DomTextField(fieldName).SetText(retrievedData_new);
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("ConcatenateandInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ConcatenateandInput finished");
    }
    
    public final void MouseKey() {
        WriteConsoleLog("MouseKey started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                switch (className) {
                    case "DomTextField":
                        objValue.DomTextField(fieldName).PressMouse(inputdata);
                        break;
                    case "DomButton":
                        objValue.DomButton(fieldName).PressMouse(inputdata);
                        break;
                    case "DomCheckBox":
                        objValue.DomCheckBox(fieldName).PressMouse(inputdata);
                        break;
                    case "DomListBox":
                        objValue.DomListBox(fieldName).PressMouse(inputdata);
                        break;
                    case "DomElement":
                        objValue.DomElement(fieldName).PressMouse(inputdata);
                        break;
                    case "DomLink":
                        objValue.DomLink(fieldName).PressMouse(inputdata);
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("MouseKey exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("MouseKey finished");
    }
    
    public final void TableSearch() {
        // //Created on 9th Jan by Jyoti for Calendar
        // value;Action;VarName
        // SceId;
        WriteConsoleLog("TableSearch started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Array tableArgs = inputdata.Split(";");
                object rowCount = objValue.DomTable(fieldName).GetRowCount;
                object ColCount = objValue.DomTable(fieldName).GetColumnCount;
                int iterator;
                int col1;
                object bolFlag = 0;
                if ((tableArgs.Length > 2)) {
                    tempScriptID = tableArgs[0];
                    strVariable = tableArgs[1];
                    tableArgs[1] = tableArgs[2];
                    retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                    tableArgs[0] = retrieved_Data;
                    if ((tableArgs[0].Substring(0, 1) == "0")) {
                        tableArgs[0] = tableArgs[0].Substring(1, 2);
                    }
                    
                }
                
                for (iterator = 0; (iterator 
                            <= (rowcount - 1)); iterator++) {
                    if ((bolFlag == 1)) {
                        // Msgbox(resultFlag)
                        break;
                    }
                    
                    for (col1 = 0; (col1 
                                <= (ColCount - 1)); col1++) {
                        object lookUpColData = objValue.DomTable(fieldName).GetCellText(iterator, col1);
                        //                 If instr(lookUpColData ,"END OF TXN") > 0 Then
                        //                     Exit Sub
                        //                 End If
                        // msgbox(lookUpColData)
                        if ((lookUpColData == tableArgs[0])) {
                            TestObject performObj = objValue.DomTable(fieldName).GetCell(iterator, col1);
                            switch (tableArgs[1].ToUpper()) {
                                case "DOMTEXTFIELD":
                                    performObj.DomTextField.SetText(tableArgs[4]);
                                    break;
                                case "DOMLINK":
                                    performObj.DomLink.Click;
                                    break;
                                case "DOMBUTTON":
                                    performObj.DomButton.Click;
                                    break;
                                case "DOMELEMENT":
                                    objValue.DomTable(fieldName).GetCell(iterator, col1).Click;
                                    resultFlag = "Pass";
                                    break;
                            }
                            bolFlag = 1;
                            break;
                        }
                        else {
                            resultFlag = "Fail";
                        }
                        
                    }
                    
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("TableSearch exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("TableSearch finished");
    }
    
    // var;modvalue;IR Numbero
    public final void ModifyDate() {
        WriteConsoleLog("ModifyDate started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object moddate;
                object IR;
                object GivenDate;
                object Days;
                IR = "";
                inputdataSplit = inputdata.Split(";");
                // msgbox (Ubound(inputdataSplit))
                if ((Ubound(inputdataSplit) == 2)) {
                    GivenDate = inputdataSplit(0);
                    Days = inputdataSplit(1);
                    IR = inputdataSplit(2);
                }
                else {
                    GivenDate = inputdataSplit(0);
                    Days = inputdataSplit(1);
                }
                
                if (((inputdata.IndexOf("LastDay") + 1) 
                            > 0)) {
                    if ((IR != "")) {
                        retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, IR, GivenDate);
                        // msgbox(retrieved_Data)
                    }
                    else {
                        retrieved_Data = GivenDate;
                    }
                    
                    DateTime appldate;
                    object SysDate;
                    retrieved_Data = retrieved_Data.Replace("-", "/");
                    // msgbox(retrieved_Data)
                    SysDate = retrieved_Data.Split("/");
                    switch (SysDate[1]) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            appldate = DateTime.Parse((31 + (" " 
                                            + (MonthName(SysDate[1]) + (" " + SysDate[2])))));
                            moddate = appldate;
                            break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            appldate = DateTime.Parse((30 + (" " 
                                            + (MonthName(SysDate[1]) + (" " + SysDate[2])))));
                            moddate = appldate;
                            break;
                        case 2:
                            if (((SysDate[2] % 4) 
                                        == 0)) {
                                appldate = DateTime.Parse((29 + (" " 
                                                + (MonthName(SysDate[1]) + (" " + SysDate[2])))));
                                moddate = appldate;
                            }
                            else {
                                appldate = DateTime.Parse((28 + (" " 
                                                + (MonthName(SysDate[1]) + (" " + SysDate[2])))));
                                moddate = appldate.ToShortDateString;
                            }
                            
                            break;
                    }
                    moddate = moddate.Replace("-", "/");
                    // msgbox(moddate)
                    SysDate = moddate.Split("/");
                    // moddate = SysDate(1) & "/" & SysDate(0) & "/" & SysDate(2)
                }
                else {
                    if ((IR != "")) {
                        retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, IR, GivenDate);
                        // msgbox(retrieved_Data)
                    }
                    else {
                        retrieved_Data = GivenDate;
                    }
                    
                    DateTime appldate;
                    object SysDate;
                    // MsgBox(retrieved_Data)
                    retrieved_Data = retrieved_Data.Replace("-", "/");
                    SysDate = retrieved_Data.Split("/");
                    appldate = DateTime.Parse((SysDate[0] + (" " 
                                    + (MonthName(SysDate[1]) + (" " + SysDate[2])))));
                    moddate = appldate.AddDays(Days).ToShortDateString;
                    moddate = moddate.Replace("-", "/");
                    // Msgbox(moddate)
                    SysDate = moddate.Split("/");
                    // moddate = SysDate(1) & "/" & SysDate(0) & "/" & SysDate(2)    
                }
                
                inputdataSplit = moddate.Split("/");
                string d_day = inputdataSplit(0);
                string m_month = inputdataSplit(1);
                string y_year = inputdataSplit(2);
                string d_day1;
                object mname;
                object d1_date = d_day.ToString.Length;
                object m1_month = m_month.ToString.Length;
                if ((d1_date == 1)) {
                    d_day1 = ("0" + d_day);
                }
                
                if ((m1_month == 1)) {
                    m_month = ("0" + m_month);
                    // msgbox(m_month)
                }
                
                object objAttribute;
                object listValue;
                objAttribute = objValue.DomElement(fieldName).GetProperty("class");
                WriteConsoleLog(("objAttribute : " + objAttribute));
                WriteConsoleLog(("d_day : " + d_day));
                switch (objAttribute) {
                    case "calendar_imageBttn":
                        WriteConsoleLog("Inside calendar_imageBttn Case");
                        objValue.DomElement(fieldName).DomClick;
                        object objcalender = silkTest.BrowserApplication(objectMapName).BrowserWindow("BrowserWindow");
                        object attrimonth = "@class=\'ui-datepicker-month\'";
                        object attriyear = "@class=\'ui-datepicker-year\'";
                        DynamicParentWait(objcalender.DomListBox(attrimonth));
                        objcalender.DomListBox(attrimonth).Select(0);
                        listValue = objcalender.DomListBox(attrimonth).text;
                        if ((listValue == "January")) {
                            mname = monthname(m_month);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        else if ((listValue == "Jan")) {
                            mname = monthname(m_month, true);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        
                        DynamicParentWait(objcalender.DomListBox(attriyear));
                        objcalender.DomListBox(attriyear).Select(y_year);
                        if (((int.Parse(d_day) > 0) 
                                    && (int.Parse(d_day) < 10))) {
                            // added to handle 01 and 1 in DD whichever is present it should click it
                            try {
                                DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                    + (int.Parse(d_day).ToString() + "\'"))));
                                objcalender.DomElement(("@textContents=\'" 
                                                + (int.Parse(d_day).ToString() + "\' and @class=\'ui-state-default*\'"))).Click;
                                // Click single digit Number converted to string i.e. instead of 01 click on 1
                            }
                            catch (Exception ex1) {
                                if (((ex1.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // if not found then append 0 and then click                            
                                    DynamicParentWait(objcalender.DomElement(("@textContents=\'0" 
                                                        + (d_day + "\' and @class=\'ui-state-default*\'"))));
                                    objcalender.DomElement(("@textContents=\'0" 
                                                    + (d_day + "\' and @class=\'ui-state-default*\'"))).Click;
                                }
                                
                            }
                            
                        }
                        else {
                            DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                + (d_day + "\' and @class=\'ui-state-default*\'"))));
                            objcalender.DomElement(("@textContents=\'" 
                                            + (d_day + "\' and @class=\'ui-state-default*\'"))).Click;
                        }
                        
                        break;
                    case "calendar_imageBttn NullValue":
                        WriteConsoleLog("calendar_imageBttn NullValue");
                        objValue.DomElement(fieldName).Click;
                        object objcalender = silkTest.BrowserApplication(objectMapName).BrowserWindow("BrowserWindow");
                        // objvalue
                        object attrimonth = "@class=\'ui-datepicker-month\'";
                        object attriyear = "@class=\'ui-datepicker-year\'";
                        DynamicParentWait(objcalender.DomListBox(attrimonth));
                        objcalender.DomListBox(attrimonth).Select(0);
                        listValue = objcalender.DomListBox(attrimonth).text;
                        if ((listValue == "January")) {
                            mname = monthname(m_month);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        else if ((listValue == "Jan")) {
                            mname = monthname(m_month, true);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        
                        DynamicParentWait(objcalender.DomListBox(attriyear));
                        objcalender.DomListBox(attriyear).Select(y_year);
                        if (((int.Parse(d_day) > 0) 
                                    && (int.Parse(d_day) < 10))) {
                            // added to handle 01 and 1 in DD whichever is present it should click it
                            try {
                                DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                    + (int.Parse(d_day).ToString() + "\' and @class=\'ui-state-default*\'"))));
                                objcalender.DomElement(("@textContents=\'" 
                                                + (int.Parse(d_day).ToString() + "\' and @class=\'ui-state-default*\'"))).DomClick;
                                // Click single digit Number converted to string i.e. instead of 01 click on 1
                            }
                            catch (Exception ex1) {
                                if (((ex1.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // if not found then append 0 and then click
                                    DynamicParentWait(objcalender.DomElement(("@textContents=\'0" 
                                                        + (d_day + "\' and @class=\'ui-state-default*\'"))));
                                    objcalender.DomElement(("@textContents=\'0" 
                                                    + (d_day + "\' and @class=\'ui-state-default*\'"))).DomClick;
                                }
                                
                            }
                            
                        }
                        else {
                            DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                + (d_day + "\' and @class=\'ui-state-default*\'"))));
                            objcalender.DomElement(("@textContents=\'" 
                                            + (d_day + "\' and @class=\'ui-state-default*\'"))).DomClick;
                        }
                        
                        break;
                    case "button":
                        WriteConsoleLog("button");
                        objValue.DomElement(fieldName).Click;
                        //                     Wait(2)
                        //                     Call DynamicWait(silkTest.BrowserApplication(objectMapName).Window("@caption='Calendar*'"))
                        object objcalender = silkTest.BrowserApplication(objectMapName).Window("@caption=\'Calendar -- Webpage Dialog\'").BrowserWindow("@windowClassName=\'Internet Explorer_Server\'");
                        //                     Dim objcalender = silkTest.BrowserApplication(objectMapName).Window("@windowClassName=Internet Explorer_TridentDlgFrame'").BrowserWindow("@windowClassName='Internet Explorer_Server'")
                        //                     //BrowserApplication//Window//BrowserWindow//Select[@id='month']
                        // objvalue
                        object attrimonth = "@id=\'month\'";
                        object attriyear = "@id=\'year\'";
                        DynamicParentWait(objcalender.DomListBox(attrimonth));
                        objcalender.DomListBox(attrimonth).Select(0);
                        listValue = objcalender.DomListBox(attrimonth).text;
                        if ((listValue == "January")) {
                            mname = monthname(m_month);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        else if ((listValue == "Jan")) {
                            mname = monthname(m_month, true);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        
                        DynamicParentWait(objcalender.DomListBox(attriyear));
                        objcalender.DomListBox(attriyear).Select(y_year);
                        d_day = int.Parse(d_day);
                        if (((int.Parse(d_day) > 0) 
                                    && (int.Parse(d_day) < 10))) {
                            // added to handle 01 and 1 in DD whichever is present it should click it
                            try {
                                DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                    + (int.Parse(d_day).ToString() + "\'"))));
                                objcalender.DomElement(("@textContents=\'" 
                                                + (int.Parse(d_day).ToString() + "\'"))).Click;
                                // Click single digit Number converted to string i.e. instead of 01 click on 1
                            }
                            catch (Exception ex1) {
                                if (((ex1.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // if not found then append 0 and then click
                                    DynamicParentWait(objcalender.DomElement(("@textContents=\'0" 
                                                        + (d_day + "\'"))));
                                    objcalender.DomElement(("@textContents=\'0" 
                                                    + (d_day + "\'"))).Click;
                                }
                                
                            }
                            
                        }
                        else {
                            DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                + (d_day + "\'"))));
                            objcalender.DomElement(("@textContents=\'" 
                                            + (d_day + "\'"))).Click;
                        }
                        
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("ModifyDate exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ModifyDate finished");
    }
    
    public final void ReplaceString() {
        //  // Created on 13 Jan for Replacing Any value in Lookupfile
        // (Scenario_id;Variable(To search from Lookupfikle);string to replace; variable to store replaced value)        
        WriteConsoleLog("ReplaceString started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object modvalue;
                // msgbox(inputdata)
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    // msgbox(tempScriptID)
                    strVariable = inputdataSplit(1);
                    modvalue = inputdataSplit(2);
                    // Dim newvariable    = inputdataSplit(3)                
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                for (i = 1; (i <= retrieved_Data.Length); i++) {
                    // msgbox(Mid(retrieved_Data,1,1))
                    if (((retrieved_Data.IndexOf(modvalue) + 1) 
                                > 0)) {
                        if (((retrieved_Data.Substring(0, 1) == " ") 
                                    || (retrieved_Data.Substring(0, 1) == modvalue))) {
                            object pos = (retrieved_Data.IndexOf(modvalue) + 1);
                            retrieved_Data = retrieved_Data.Substring(pos, retrieved_Data.Length);
                            // msgbox(retrieved_Data)
                        }
                        else {
                            break;
                        }
                        
                    }
                    
                }
                
                inputData = inputdataSplit(3);
                fieldData = retrieved_Data;
                WriteToLookupFile();
                actualMessage = "Value stored in lookup file";
            }
            catch (Exception ex) {
                WriteConsoleLog(("ReplaceString exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ReplaceString finished");
    }
    
    // scenario_id;var;incrementvalue;Case(MOnth or Year),VarToStore
    public final void IncrementDate() {
        WriteConsoleLog("IncrementDate started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object modvalue;
                object varcase;
                object moddate;
                // msgbox(inputdata)
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    // msgbox(tempScriptID)
                    strVariable = inputdataSplit(1);
                    modvalue = inputdataSplit(2);
                    varcase = inputdataSplit(3);
                    VarToStore = inputdataSplit(4);
                    // msgbox(inputdataSplit(0) & inputdataSplit(1) & inputdataSplit(2))                
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                DateTime appldate;
                // msgbox("'" + retrieved_Data + "'")
                object arrRetDate = retrieved_Data.Split("/");
                switch (System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern.ToUpper()) {
                    case "DD-MM-YYYY":
                    case "DD-M-YYYY":
                    case "D-MM-YYYY":
                    case "D-M-YYYY":
                    case "DD/MM/YYYY":
                    case "DD/M/YYYY":
                    case "D/MM/YYYY":
                    case "D/M/YYYY":
                        retrieved_Data = (arrRetDate[0] + ("/" 
                                    + (arrRetDate[1] + ("/" + arrRetDate[2]))));
                        break;
                    case "MM-DD-YYYY":
                    case "M/DD/YYYY":
                    case "MM/D/YYYY":
                    case "M/D/YYYY":
                    case "MM/DD/YYYY":
                    case "M/DD/YYYY":
                    case "MM/D/YYYY":
                    case "M/D/YYYY":
                        retrieved_Data = (arrRetDate[1] + ("/" 
                                    + (arrRetDate[0] + ("/" + arrRetDate[2]))));
                        break;
                    default:
                        WriteConsoleLog(("Please add the new date format in the IncrementDate funtion. " + System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern.ToUpper()));
                        resultFlag = "Fail";
                        actualMessage = ("Please add the new date format in the IncrementDate funtion. " + System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern.ToUpper());
                        break;
                }
                //         retrieved_Data = arrRetDate(1) + "/" + arrRetDate(0) + "/" + arrRetDate(2) 'Updated the date format from dd/mm/yyyy to mm/dd/yyyy as CDate was throwing exception
                //         msgbox(retrieved_Data)
                appldate = DateTime.Parse(retrieved_Data);
                varcase = varcase.ToUpper();
                switch (varcase) {
                    case "MONTH":
                        moddate = appldate.AddMonths(modvalue).ToShortDateString;
                        // msgbox(moddate)
                        moddate = moddate.Replace("-", "/");
                        break;
                    case "YEAR":
                        moddate = appldate.AddYears(modvalue).ToShortDateString;
                        moddate = moddate.Replace("-", "/");
                        // msgbox(moddate)    
                        break;
                    case "DAYS":
                        moddate = appldate.AddDays(modvalue).ToShortDateString;
                        moddate = moddate.Replace("-", "/");
                        // msgbox(moddate)        
                        break;
                }
                scenarioID = tempScriptID;
                inputData = inputdataSplit(4);
                object arrInputdata;
                arrInputdata = moddate.Split("/");
                if ((arrInputdata[0].Length == 1)) {
                    arrInputdata[0] = (("0" + arrInputdata[0].ToString())).ToString();
                    moddate = (arrInputdata[0] + ("/" 
                                + (arrInputdata[1] + ("/" + arrInputdata[2]))));
                }
                
                if ((arrInputdata[1].Length == 1)) {
                    arrInputdata[1] = (("0" + arrInputdata[1].ToString())).ToString();
                    moddate = (arrInputdata[0] + ("/" 
                                + (arrInputdata[1] + ("/" + arrInputdata[2]))));
                }
                
                arrRetDate = moddate.Split("/");
                switch (System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern.ToUpper()) {
                    case "DD-MM-YYYY":
                    case "DD-M-YYYY":
                    case "D-MM-YYYY":
                    case "D-M-YYYY":
                    case "DD/MM/YYYY":
                    case "DD/M/YYYY":
                    case "D/MM/YYYY":
                    case "D/M/YYYY":
                        fieldData = (arrRetDate[0] + ("/" 
                                    + (arrRetDate[1] + ("/" + arrRetDate[2]))));
                        break;
                    case "MM-DD-YYYY":
                    case "M/DD/YYYY":
                    case "MM/D/YYYY":
                    case "M/D/YYYY":
                    case "MM/DD/YYYY":
                    case "M/DD/YYYY":
                    case "MM/D/YYYY":
                    case "M/D/YYYY":
                        fieldData = (arrRetDate[1] + ("/" 
                                    + (arrRetDate[0] + ("/" + arrRetDate[2]))));
                        break;
                    default:
                        WriteConsoleLog(("Please add the new date format in the IncrementDate funtion. " + System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern.ToUpper()));
                        resultFlag = "Fail";
                        actualMessage = ("Please add the new date format in the IncrementDate funtion. " + System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern.ToUpper());
                        break;
                }
                //             fieldData = arrRetDate(1) + "/" + arrRetDate(0) + "/" + arrRetDate(2)
                // MsgBox(moddate)
                WriteToLookupFile();
            }
            catch (Exception ex) {
                WriteConsoleLog(("IncrementDate exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("IncrementDate finished");
    }
    
    public final void LaunchPutty() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Process ps;
                for (ps : Process.GetProcesses) {
                    if ((ps.ProcessName == "putty")) {
                        ps.Kill;
                    }
                    
                    if ((ps.ProcessName == "Putty  (32 bit)")) {
                        ps.Kill;
                    }
                    
                }
                
                Process.Start(inputdata);
                Agent.Attach("putty.exe", "");
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void PuttyInput() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                // msgbox(inputdata)
                // msgbox(fieldName)                                            
                if ((fieldName == "Host Name")) {
                    objvalue.TextField(fieldName).Click();
                    objvalue.TextField(fieldName).TypeKeys("");
                    objvalue.TextField(fieldName).TypeKeys(inputdata);
                }
                else {
                    silkTest.Window(objectMapName).SetActive();
                    silkTest.Window(objectMapName).TypeKeys("");
                    silkTest.Window(objectMapName).TypeKeys(inputdata);
                }
                
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void PuttyRetInput() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                silkTest.Window(objectMapName).SetActive();
                silkTest.Window(objectMapName).TypeKeys("");
                silkTest.Window(objectMapName).TypeKeys("");
                silkTest.Window(objectMapName).TypeKeys(retrieved_Data);
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void PuttyPressKey() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                silkTest.Window(objectMapName).SetActive();
                silkTest.Window(objectMapName).TypeKeys(("<" 
                                + (inputdata + ">")));
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void RANDOMAADHARENROLLDTLS() {
        WriteConsoleLog("RANDOMAADHARENROLLDTLS started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number;
            string AadharPart1 = "";
            for (i = 1; (i <= 5); i++) {
                AadharPart1 = (AadharPart1 + rmd.next(99999, 999999).ToString());
            }
            
            inputdata = AadharPart1.Substring(0, 28);
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RANDOMAADHARENROLLDTLS exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RANDOMAADHARENROLLDTLS finished");
    }
    
    public final void RANDOMAADHARNUMBER() {
        WriteConsoleLog("RANDOMAADHARNUMBER started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number;
            string AadharPart1 = "";
            for (i = 1; (i <= 5); i++) {
                AadharPart1 = (AadharPart1 + rmd.next(99999, 999999).ToString());
            }
            
            inputdata = AadharPart1.Substring(0, 12);
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RANDOMAADHARNUMBER exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RANDOMAADHARNUMBER finished");
    }
    
    // // Random value generation
    public final void RandomInput() {
        WriteConsoleLog("RandomInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number = rmd.next(99999, 999999);
            if ((inputdata == "")) {
                //  Incase there is no appending required at the beginning of the string
                inputdata = ("TE" + rmd_number);
            }
            else {
                // msgbox(CStr(rmd_number))
                inputdata = (inputdata + rmd_number.ToString());
            }
            
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    objValue.DomTextField(fieldName).SetText("");
                    objValue.DomTextField(fieldName).SetText(inputdata);
                }
                
                newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    actualMessage = "Unable to enter data in the field";
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RandomInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RandomInput finished");
    }
    
    public final void RandomFourDigitInput() {
        WriteConsoleLog("RandomFourDigitInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number = rmd.next(10000);
            if ((inputdata == "")) {
                //  Incase there is no appending required at the beginning of the string
                //  inputdata = rmd_number
            }
            else {
                // msgbox(CStr(rmd_number))
                // inputdata = inputdata + CStr(rmd_number)
                inputdata = rmd_number;
                //             msgbox(inputdata)
            }
            
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    objValue.DomTextField(fieldName).SetText("");
                    objValue.DomTextField(fieldName).SetText(inputdata);
                }
                
                newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    actualMessage = "Unable to enter data in the field";
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RandomFourDigitInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RandomFourDigitInput finished");
    }
    
    public final void GenerateRandom() {
        WriteConsoleLog("GenerateRandom started");
        // To generate Random number  og any length
        // P;3----Result:P526
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            string ConcateText;
            int Randomlength;
            int number;
            inputdataSplit = inputdata.Split(";");
            if ((UBound(inputdataSplit) == 1)) {
                ConcateText = inputdataSplit(0);
                Randomlength = inputdataSplit(1);
                number = (10 | Randomlength);
                // TODO: Warning!!! The operator should be an XOR ^ instead of an OR, but not available in CodeDOM
                int rmd_number = rmd.next(number);
                inputdata = (inputdataSplit(0) + rmd_number.ToString());
            }
            else {
                Randomlength = inputdataSplit(0);
                number = (10 | Randomlength);
                // TODO: Warning!!! The operator should be an XOR ^ instead of an OR, but not available in CodeDOM
                int rmd_number = rmd.next(number);
                inputdata = rmd_number.ToString();
            }
            
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    objValue.DomTextField(fieldName).SetText("");
                    objValue.DomTextField(fieldName).SetText(inputdata);
                }
                
                newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    actualMessage = "Unable to enter data in the field";
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("GenerateRandom exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("GenerateRandom finished");
    }
	 public final void RandomThreeDigitInput() {
        WriteConsoleLog("RandomFourDigitInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number = rmd.next(1000);
            if ((inputdata == "")) {
                //  Incase there is no appending required at the beginning of the string
                //  inputdata = rmd_number
            }
            else {
                // msgbox(CStr(rmd_number))
                // inputdata = inputdata + CStr(rmd_number)
                inputdata = rmd_number;
                //             msgbox(inputdata)
            }
            
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    objValue.DomTextField(fieldName).SetText("");
                    objValue.DomTextField(fieldName).SetText(inputdata);
                }
                
                newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    actualMessage = "Unable to enter data in the field";
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RandomFourDigitInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RandomFourDigitInput finished");
    }
    
    public final void RandomLEDNO() {
        WriteConsoleLog("RandomFourDigitInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number = rmd.next(100);
            if ((inputdata == "")) {
                //  Incase there is no appending required at the beginning of the string
                //  inputdata = rmd_number
            }
            else {
                // msgbox(CStr(rmd_number))
                // inputdata = inputdata + CStr(rmd_number)
                string temptext;
                string text;
                text = rmd_number;
                temptext = "ABCD99GHIJKLNMOPSR";
                temptext = (temptext + text);
                inputdata = temptext;
            }
            
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    objValue.DomTextField(fieldName).SetText("");
                    objValue.DomTextField(fieldName).SetText(inputdata);
                }
                
                newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    actualMessage = "Unable to enter data in the field";
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RandomFourDigitInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RandomFourDigitInput finished");
    }
    
    public final void RandomSerialNO() {
        WriteConsoleLog("RandomSerialNO started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            if (inputdata.ToString.Contains("#")) {
                if ((inputdata.ToString.Split("#").Length > 1)) {
                    object temptext = inputdata.ToString.Split("#")[1];
                    inputdata = temptext;
                }
                
            }
            
            Random rmd = new Random();
            int rmd_number;
            rmd_number = rmd.next(1000);
            // msgbox(rmd_number)
            // msgbox(rmd_number.ToString.Length)
            if ((rmd_number.ToString.Length < 3)) {
                rmd_number = rmd.next(1000);
            }
            
            char s;
            string intval;
            if ((inputdata == "")) {
                //  Incase there is no appending required at the beginning of the string
                //  inputdata = rmd_number
            }
            else {
                // msgbox(CStr(rmd_number))
                string sb = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                Randomize();
                intval = Fix((26 * Rnd()));
                int idx = rmd.Next(0, 35);
                s = sb.Substring(intval, 1);
                //             
                inputdata = (inputdata 
                            + (s + rmd_number.ToString()));
            }
            
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    objValue.DomTextField(fieldName).SetText("");
                    objValue.DomTextField(fieldName).SetText(inputdata);
                }
                
                newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    actualMessage = "Unable to enter data in the field";
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RandomSerialNO exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RandomSerialNO finished");
    }
    
    public final void DoubleClick() {
        // //Created Date: 9-Feb-2015
        // //Drecription:     For double click on Browser object only
        // //Parameters:  No parameter    
        WriteConsoleLog("DoubleClick started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                switch (className) {
                    case "DomTextField":
                        objValue.DomTextField(fieldName).DomDoubleClick;
                        break;
                    case "DomButton":
                        objValue.DomButton(fieldName).DomDoubleClick;
                        break;
                    case "DomCheckBox":
                        objValue.DomCheckBox(fieldName).DomDoubleClick;
                        break;
                    case "DomListBox":
                        objValue.DomListBox(fieldName).DomDoubleClick;
                        break;
                    case "DomElement":
                        objValue.DomElement(fieldName).DomDoubleClick;
                        break;
                    case "DomLink":
                        objValue.DomLink(fieldName).DomDoubleClick;
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("DoubleClick exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("DoubleClick finished");
    }
    
    // //Created on 29-Jan-2015 for creating file. eg(CIL,BLK files)
    public final void fileUploadVbs() {
        object retrieved_Data1;
        object retrieved_Data2;
        object retrieved_Data3;
        object strVariable1;
        object strVariable2;
        object strVariable3;
        object strVariable4;
        // Dim exetime1         
        tempScriptID = scenarioID;
        inputdataSplit = inputdata.Split(";");
        strVariable1 = inputdataSplit(1);
        strVariable2 = inputdataSplit(2);
        strVariable3 = inputdataSplit(3);
        strVariable4 = inputdataSplit(4);
        fieldData = (fileUploadPath 
                    + (strVariable1 
                    + (DateTime.Today.ToString("dd-MMM-yyyy") + ".txt")));
        inputData = strVariable1;
        WriteToLookupFile();
        retrieved_Data1 = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, StrVariable2);
        retrieved_Data2 = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable3);
        retrieved_Data3 = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable4);
        psiFile.FileName = fileUploadVbsPath;
        psiFile.Arguments = (retrieved_Data1 + (" " 
                    + (retrieved_Data2 + (" " 
                    + (retrieved_Data3 + (" " + fieldData))))));
        Workbench.ResultComment(executionLogPath);
        Process.Start(psiFile);
    }
    
    public final void TypeData() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object arrInputdata;
                object valueOfarrInputdata;
                object listValue;
                object iterator;
                object bFlag;
                arrInputdata = "";
                bFlag = false;
                if (((inputdata.IndexOf(":") + 1) 
                            > 0)) {
                    // And instr(inputdata,"<") > 0 Then
                    arrInputdata = inputdata.Split(":");
                }
                else if (((inputdata.IndexOf("<") + 1) 
                            > 0)) {
                    arrInputdata = inputdata.Split("<");
                }
                else if (((inputdata.IndexOf("-") + 1) 
                            > 0)) {
                    arrInputdata = inputdata.Split("-");
                }
                else if (((inputdata.IndexOf("/") + 1) 
                            > 0)) {
                    arrInputdata = inputdata.Split("/");
                }
                
                listValue = objValue.DomListBox(fieldName).text;
                if ((listValue != inputdata)) {
                    objValue.DomListBox(fieldName).Click;
                    //  Type data in Listbox
                    if ((arrInputdata.length > 0)) {
                        objValue.DomListBox(fieldName).PressKeys(arrInputdata[0]);
                        valueOfarrInputdata = arrInputdata[0];
                    }
                    else {
                        objValue.DomListBox(fieldName).PressKeys(inputdata);
                        valueOfarrInputdata = inputdata;
                    }
                    
                }
                
                listValue = objValue.DomListBox(fieldName).text;
                listValue = listValue.Trim();
                inputdata = inputdata.Trim();
                if ((((listValue.IndexOf(inputdata) + 1) 
                            > 0) 
                            || ((inputdata.IndexOf(listValue) + 1) 
                            > 0))) {
                    bFlag = true;
                }
                
                if ((bFlag == false)) {
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                    // recoveryFlag=1
                    WriteToOutputFile();
                }
                
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void FileReadToResetTeller() {
        if ((inputdata == "User No")) {
            
        }
        
        objFSO = createobject("Scripting.FileSystemObject");
        objFile = objFSO.OpenTextFile("C:\\SBI_SilkTest\\SBI_ARTP\\Vbs_Files\\ResetTeller.CSV");
        while (!objFile.AtEndOfStream) {
            if ((resultFlag != "Fail")) {
                break;
            }
            
            // actualMessage = "Same as Expected"
            fileData = objFile.ReadLine;
            arrData = fileData.Split(",");
            if (((fileData.IndexOf("Function Description") + 1) 
                        == 0)) {
                runStatus = arrData(18);
                if ((runStatus.ToUpper() == "YES")) {
                    scenarioID = arrData(2);
                    scenarioDescription = arrData(3);
                    scripID = arrData(8);
                    stepID = arrData(10);
                    stepDescription = arrData(11);
                    stepExpectedResult = arrData(15);
                    objectMapName = arrData(17);
                    fieldName = arrData(12);
                    keyword = arrData(15).ToUpper();
                    FieldConstruction = arrData(19);
                    inputdata = arrData(13).Replace("^", ",");
                }
                
            }
            
        }
        
        objFile.Close;
    }
    
    public final void InputKeyBoardEvent() {
        WriteConsoleLog("InputKeyBoardEvent started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            object text1;
            try {
                switch (className) {
                    case "DomTextField":
                        inputdata = inputdata.Trim();
                        objValue.DomTextField(fieldName).PressKeys(("<" 
                                        + (inputdata + ">")));
                        // objValue.DomTextField(fieldName).PressKeys("<Enter>")            
                        break;
                    case "DomListBox":
                        inputdata = inputdata.Trim();
                        objValue.DomElement(fieldName).TypeKeys(inputdata);
                        objValue.DomElement(fieldName).PressKeys("<Down>");
                        objValue.DomElement(fieldName).PressKeys("<Enter>");
                        text1 = objValue.DomElement(fieldName).Text;
                        text1 = text1.Trim();
                        if ((text1 != inputdata)) {
                            resultFlag = "Fail";
                        }
                        
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("InputKeyBoardEvent exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("InputKeyBoardEvent finished");
    }
    
    final void CopyFileFromRemoteServer() {
        // //Copied the text from one file to another file
        // IR-75548-001;fileNameforBulk    
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object sSource;
                object sDestination;
                object filesys;
                object fileSource;
                object fileDestination;
                // sSource = "\\10.0.14.209\ftp_10.0.14.209\"
                sSource = "\\\\10.0.14.205\\ftp\\";
                sDestination = "C:\\SBI_SilkTest\\SBI_ARTP\\FileCopy\\";
                Array ArrInput = inputdata.Split(";");
                // msgbox(ArrInput.Length)
                if ((ArrInput.Length >= 2)) {
                    tempScriptID = ArrInput[0];
                    strVariable = ArrInput[1];
                }
                else {
                    tempScriptID = prevScriptID;
                    strVariable = ArrInput[0];
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                // msgbox(retrieved_Data)
                fileSource = (sSource + retrieved_Data);
                // msgbox(fileSource)
                fileDestination = sDestination;
                // msgbox(fileDestination)
                filesys = CreateObject("Scripting.FileSystemObject");
                // msgbox(fileSource)
                if (filesys.FileExists(fileSource)) {
                    filesys.CopyFile(fileSource, fileDestination);
                }
                
                filesys = null;
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    final void RetrieveDataFromReport() {
        // //Retrieve and Read the CIF No and Acct No from the text file to Excel
        // SCR-BLK-001;3000;sStoreVariable;FileName    
        WriteConsoleLog("RetrieveDataFromReport started");
        object myTextFile;
        object filesys;
        object arrLines;
        object strLine;
        object myTextData;
        object iCount;
        object retrieved_FileName;
        object jCount;
        object arrString;
        object sStoreVariable;
        object myTextFileVar;
        const object ForReading = 1;
        jCount = 1;
        Array ArrInput = inputdata.Split(";");
        if ((ArrInput.Length >= 3)) {
            tempScriptID = ArrInput[0];
            strVariable = ArrInput[1];
            sStoreVariable = ArrInput[2];
            myTextFileVar = ArrInput[3];
            // msgbox(myTextFileVar)
        }
        
        retrieved_FileName = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, myTextFileVar);
        if ((retrieved_FileName != "")) {
            myTextFile = retrieved_FileName;
        }
        else {
            myTextFile = myTextFileVar;
        }
        
        myTextFile = ("C:\\SBI_SilkTest\\SBI_ARTP\\FileCopy\\" + myTextFile);
        // msgbox(myTextFile)
        filesys = CreateObject("Scripting.FileSystemObject");
        // Reading the content from the text file
        myTextData = filesys.OpenTextFile(myTextFile, ForReading).ReadAll;
        // Split the text file into the lines
        arrLines = myTextData.Split("\r\n");
        // Step through the lines
        for (strLine : arrLines) {
            if (((strLine.IndexOf(strVariable) + 1) 
                        != 0)) {
                arrString = strLine.Split(" ");
                for (iCount = 0; (iCount 
                            <= (arrString.length - 1)); iCount++) {
                    // msgbox(Left(instr(1,arrString(iCount),strVariable,1),Len(strVariable)))        
                    // msgbox(Left(arrString(iCount),Len(strVariable)))
                    if (((arrString[iCount].Substring(0, strVariable.Length).IndexOf(strVariable) + 1) 
                                != 0)) {
                        //     msgbox()
                        fieldData = arrString[iCount].Trim();
                        inputdata = (sStoreVariable + ("-" + jCount));
                        jCount = (jCount + 1);
                        WriteToLookupFile();
                    }
                    
                }
                
            }
            
        }
        
        filesys = null;
        WriteConsoleLog("RetrieveDataFromReport finished");
    }
    
    // TMOUT=0;bat;lsrt:BLK:fileNameforBulk
    public final void pLinkFunction() {
        object Wshshell;
        object oExec;
        object input;
        object objCmd;
        object Pline;
        object pLinkCmd;
        object wholeOuput;
        object cmdAndVerifyDataSplit;
        object iCount;
        object arrCommand;
        object arrVerifyData;
        object arrstoreDataName;
        object jCount;
        object jCounter;
        Process ps;
        object retrieved_Data1;
        object retrieved_Data2;
        object retrieved_Data3;
        object wholeOuput1;
        object arrwholeOuput;
        object strVariable1;
        object strVariable2;
        object strVariable3;
        object strVariable4;
        jCount = 0;
        jCounter = 0;
        Wshshell = CreateObject("Wscript.Shell");
        pLinkCmd = "C:\\Users\\TG1862\\Desktop\\plink.exe -ssh -t -l fnsonlkd -pw India@1 10.0.14.253";
        objCmd = Wshshell.Exec(pLinkCmd);
        inputdataSplit = inputdata.Split(";");
        for (iCount = 0; (iCount 
                    <= (inputdataSplit.length - 1)); iCount++) {
            cmdAndVerifyDataSplit = inputdataSplit(iCount).Split(":");
            arrCommand[iCount] = cmdAndVerifyDataSplit[0];
            if ((cmdAndVerifyDataSplit.length > 1)) {
                // arrCommand(iCount) = cmdAndVerifyDataSplit(0)
                arrVerifyData[jCounter] = cmdAndVerifyDataSplit[1];
                arrstoreDataName[jCounter] = cmdAndVerifyDataSplit[2];
                jCounter = (jCounter + 1);
            }
            
        }
        
        while (!objCmd.StdOut.AtEndOfStream) {
            Pline = objCmd.StdOut.ReadLine;
            wholeOuput = (wholeOuput + Pline);
            if (((Pline.IndexOf(arrVerifyData[jCount]) + 1) 
                        != 0)) {
                if ((!(arrstoreDataName[jCount] == "") 
                            && !(arrVerifyData[jCount] == ""))) {
                    inputData = arrstoreDataName[jCount];
                    fieldData = Pline;
                    // msgbox(fieldData  + "  fieldData")
                    WriteToLookupFile();
                    jCount = (jCount + 1);
                }
                
            }
            
            if (!((Pline.IndexOf("/home/fnsonlkd") + 1) 
                        == 0)) {
                if ((inputdataSplit.length > 1)) {
                    for (iCount = 0; (iCount 
                                <= (arrCommand.length - 1)); iCount++) {
                        if (!(arrCommand[iCount] == "")) {
                            // msgbox(arrCommand(iCount))
                            objCmd.StdIn.Writeline(arrCommand[iCount]);
                        }
                        
                    }
                    
                    objCmd.StdIn.Writeline("exit");
                }
                
            }
            
            //         If Instr(Pline,sSearchData) <> 0 Then
            //                 wholeOuput = ""
            //         End If
        }
        
        // msgbox(wholeOuput)    
        if (((wholeOuput.IndexOf("> SQL> Disconnected from Oracle Database 11g") + 1) 
                    != 0)) {
            arrwholeOuput = wholeOuput.Split("> SQL> Disconnected from Oracle Database 11g");
            wholeOuput1 = arrwholeOuput[0];
            wholeOuput1 = wholeOuput.Replace("-", "");
        }
        
        fieldData = wholeOuput;
        WriteToLookupFile();
    }
    
    // TMOUT=0;bat;lsrt:BLK:fileNameforBulk
    public final void pLinkFunctionStoreOutputFromSQL() {
        object Wshshell;
        object oExec;
        object input;
        object objCmd;
        object Pline;
        object pLinkCmd;
        object wholeOuput;
        object cmdAndVerifyDataSplit;
        object arrCommand;
        object arrVerifyData;
        object arrstoreDataName;
        object jCount;
        object jCounter;
        object retrieved_query;
        object sSearchData;
        Process ps;
        object arrwholeOuput;
        object iCount;
        object iCounter;
        // bflag = False
        iCounter = 0;
        jCount = 0;
        jCounter = 0;
        tempScriptID = scenarioID;
        Wshshell = CreateObject("Wscript.Shell");
        pLinkCmd = "C:\\Users\\TG1862\\Desktop\\plink.exe -ssh -t -l fnsonlkd -pw India@1 10.0.14.253";
        objCmd = Wshshell.Exec(pLinkCmd);
        inputdataSplit = inputdata.Split(":");
        inputData = inputdataSplit(1);
        arrCommand = inputdataSplit(0).Split(";");
        strVariable = arrCommand[2];
        sSearchData = arrCommand[0];
        retrieved_query = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
        if ((retrieved_query != "")) {
            arrCommand[2] = retrieved_query;
        }
        
        object sflag = false;
        while (!objCmd.StdOut.AtEndOfStream) {
            Pline = objCmd.StdOut.ReadLine;
            wholeOuput = (wholeOuput + Pline);
            if ((sflag == false)) {
                if (!((Pline.IndexOf("/home/fnsonlkd") + 1) 
                            == 0)) {
                    objCmd.StdIn.Writeline("TIMEOUT=0");
                    for (iCount = 1; (iCount 
                                <= (arrCommand.length - 1)); iCount++) {
                        if ((arrCommand[iCount] != "")) {
                            objCmd.StdIn.Writeline(arrCommand[iCount]);
                        }
                        
                    }
                    
                    sflag = true;
                    objCmd.StdIn.Writeline("exit");
                }
                
            }
            
            if (((Pline.IndexOf(sSearchData) + 1) 
                        != 0)) {
                wholeOuput = "";
                iCounter = (iCounter + 1);
                if (((Pline.IndexOf(".prt") + 1) 
                            != 0)) {
                    break;
                }
                
            }
            
            // msgbox(wholeOuput)
        }
        
        if (((wholeOuput.IndexOf("> SQL> Disconnected from Oracle Database 11g") + 1) 
                    != 0)) {
            arrwholeOuput = wholeOuput.Split("> SQL> Disconnected from Oracle Database 11g");
            wholeOuput = arrwholeOuput[0];
            wholeOuput = wholeOuput.Replace("-", "");
        }
        else {
            wholeOuput = Pline;
        }
        
        fieldData = wholeOuput;
        WriteToLookupFile();
    }
    
    public final void WindowRetInput() {
        // //Drecription:     Retrive data from lookup file and enter into windows pop-up other than application eg.Save As pop
        // //Parameters:  1 parameter(for enter data in textField)
        WriteConsoleLog("WindowRetInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                WindowInput();
            }
            catch (Exception ex) {
                WriteConsoleLog(("WindowRetInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("WindowRetInput finished");
    }
    
    public final void WindowInput() {
        // //Drecription:     Click on button or Enter data in windows pop-up other than application eg.Save As pop
        //                  This keyword handle textfield and PushButton
        // //Parameters:  1 parameter(for enter data in textField)    
        WriteConsoleLog("WindowInput started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            if ((retrieved_Data != "")) {
                inputdata = retrieved_Data;
            }
            
            try {
                switch (className) {
                    case "TextField":
                        fielddata = objValue.TextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.TextField(fieldName).TypeKeys(inputdata);
                        }
                        else {
                            objValue.TextField(fieldName).SetText("");
                            objValue.TextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                    case "PushButton":
                        objValue.PushButton(fieldName).Click;
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("WindowInput exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("WindowInput finished");
    }
    
    // For Increment In time as per current time
    // 'scenario_id;incrementvalue;Case(MINS or HRS or NOW),VarToStore
    public final void IncrementTime() {
        WriteConsoleLog("IncrementTime started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object modvalue;
                object varcase;
                object modtime;
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    // msgbox(tempScriptID)                
                    modvalue = inputdataSplit(1);
                    varcase = inputdataSplit(2);
                    VarToStore = inputdataSplit(3);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                DateTime Datevalue = Now;
                switch (varcase) {
                    case "MINS":
                        modtime = Datevalue.AddMinutes(modvalue).ToShortTimeString;
                        // msgbox(modtime)                        
                        break;
                    case "HRS":
                        modtime = Datevalue.AddHours(modvalue).ToShortTimeString;
                        // msgbox(modtime)
                        break;
                    case "NOW":
                        modtime = Datevalue.ToShortTimeString;
                        // msgbox(modtime)                    
                        break;
                }
                scenarioID = tempScriptID;
                inputData = VarToStore;
                fieldData = modtime;
                if (((fieldName.IndexOf("End Time") + 1) 
                            > 0)) {
                    fieldData = modtime.Replace(":", "");
                    // msgbox(fieldData)
                }
                else {
                    fieldData = modtime;
                }
                
                WriteToLookupFile();
            }
            catch (Exception ex) {
                WriteConsoleLog(("IncrementTime exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("IncrementTime finished");
    }
    
    // TMOUT=0;bat;lsrt:BLK:fileNameforBulk
    public final void pLinkFunctionStoreOutputFromSQLLast() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Process ps;
                for (ps : Process.GetProcesses) {
                    if ((ps.ProcessName == "putty")) {
                        ps.Kill;
                    }
                    
                    if ((ps.ProcessName == "Command-line SSH, Telnet, and Rlogin client")) {
                        ps.Kill;
                    }
                    
                }
                
                object sLastFileName;
                object arrVerifyData;
                object arrstoreDataName;
                object jCount;
                object jCounter;
                object retrieved_query;
                object sSearchData;
                object Wshshell;
                object oExec;
                object input;
                object objCmd;
                object Pline;
                object pLinkCmd;
                object wholeOuput;
                object cmdAndVerifyDataSplit;
                object arrCommand;
                object arrwholeOuput;
                object iCount;
                object iCounter;
                iCounter = 0;
                jCount = 0;
                jCounter = 0;
                tempScriptID = scenarioID;
                Wshshell = CreateObject("Wscript.Shell");
                pLinkCmd = "C:\\Users\\TG1862\\Desktop\\plink.exe -ssh -t -l fnsonlkd -pw India@1 10.0.14.253";
                objCmd = Wshshell.Exec(pLinkCmd);
                inputdataSplit = inputdata.Split(":");
                inputData = inputdataSplit(1);
                arrCommand = inputdataSplit(0).Split(";");
                strVariable = arrCommand[2];
                sSearchData = arrCommand[0];
                retrieved_query = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                if ((retrieved_query != "")) {
                    arrCommand[2] = retrieved_query;
                }
                
                object sflag = false;
                while (!objCmd.StdOut.AtEndOfStream) {
                    Pline = objCmd.StdOut.ReadLine;
                    wholeOuput = (wholeOuput + Pline);
                    if ((sflag == false)) {
                        if (!((Pline.IndexOf("/home/fnsonlkd") + 1) 
                                    == 0)) {
                            objCmd.StdIn.Writeline("TIMEOUT=0");
                            for (iCount = 1; (iCount 
                                        <= (arrCommand.length - 1)); iCount++) {
                                if ((arrCommand[iCount] != "")) {
                                    objCmd.StdIn.Writeline(arrCommand[iCount]);
                                }
                                
                            }
                            
                            sflag = true;
                            objCmd.StdIn.Writeline("exit");
                        }
                        
                    }
                    
                    if (((Pline.IndexOf(sSearchData) + 1) 
                                != 0)) {
                        sLastFileName = Pline;
                    }
                    
                }
                
                if (((wholeOuput.IndexOf("> SQL> Disconnected from Oracle Database 11g") + 1) 
                            != 0)) {
                    arrwholeOuput = wholeOuput.Split("> SQL> Disconnected from Oracle Database 11g");
                    wholeOuput = arrwholeOuput[0];
                    wholeOuput = wholeOuput.Replace("-", "");
                    sLastFileName = wholeOuput;
                }
                
                fieldData = sLastFileName;
                WriteToLookupFile();
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    // IR-75548-001;3000;fileNameforBulk;PROCESSED
    public final void pLinkFunctionToVerifyMessageFromReport() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object myTextFile;
                object filesys;
                object arrLines;
                object strLine;
                object myTextData;
                object iCount;
                object retrieved_FileName;
                object jCount;
                object arrString;
                object sStoreVariable;
                object myTextFileVar;
                object jCounter;
                object sVerifyMessage;
                const object ForReading = 1;
                jCount = 1;
                Array ArrInput = inputdata.Split(";");
                if ((ArrInput.Length >= 3)) {
                    tempScriptID = ArrInput[0];
                    strVariable = ArrInput[1];
                    myTextFileVar = ArrInput[2];
                    sVerifyMessage = ArrInput[3];
                }
                else {
                    strVariable = ArrInput[0];
                    myTextFileVar = ArrInput[1];
                    sVerifyMessage = ArrInput[2];
                }
                
                retrieved_FileName = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, myTextFileVar);
                if ((retrieved_FileName != "")) {
                    myTextFile = retrieved_FileName;
                }
                else {
                    myTextFile = myTextFileVar;
                }
                
                myTextFile = ("C:\\SBI_SilkTest\\SBI_ARTP\\FileCopy\\" + myTextFile);
                filesys = CreateObject("Scripting.FileSystemObject");
                // Reading the content from the text file
                myTextData = filesys.OpenTextFile(myTextFile, ForReading).ReadAll;
                // Split the text file into the lines
                arrLines = myTextData.Split("\r\n");
                // Step through the lines
                for (strLine : arrLines) {
                    if (((strLine.IndexOf(strVariable) + 1) 
                                != 0)) {
                        if (((strLine.IndexOf(sVerifyMessage) + 1) 
                                    == 0)) {
                            SetRecoveryAndResultFlag();
                        }
                        
                    }
                    
                }
                
                filesys = null;
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    final void VerifyDataInReport() {
        // //Retrieve the entire record text file to Excel
        // SCR-BLK-001;Variable to search for;FileName;Variable to Store the record    
        WriteConsoleLog("VerifyDataInReport started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object myTextFile;
                object filesys;
                object arrLines;
                object strLine;
                object myTextData;
                object iCount;
                object retrieved_FileName;
                object jCount;
                object arrString;
                object sStoreVariable;
                object myTextFileVar;
                const object ForReading = 1;
                jCount = 1;
                Array ArrInput = inputdata.Split(";");
                if ((ArrInput.Length >= 3)) {
                    tempScriptID = ArrInput[0];
                    strVariable = ArrInput[1];
                    myTextFileVar = ArrInput[2];
                    sStoreVariable = ArrInput[3];
                }
                
                retrieved_FileName = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, myTextFileVar);
                // msgbox(retrieved_FileName)
                if ((retrieved_FileName != "")) {
                    myTextFile = retrieved_FileName;
                }
                else {
                    myTextFile = ArrInput[1];
                }
                
                strVariable = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                myTextFile = ("C:\\SBI_SilkTest\\SBI_ARTP\\FileCopy\\" + myTextFile);
                filesys = CreateObject("Scripting.FileSystemObject");
                // Reading the content from the text file
                myTextData = filesys.OpenTextFile(myTextFile, ForReading).ReadAll;
                // Split the text file into the lines
                arrLines = myTextData.Split("\r\n");
                // Step through the lines    
                for (iCount = 0; (iCount 
                            <= (arrLines.length - 1)); iCount++) {
                    if (((arrLines[iCount].IndexOf(strVariable) + 1) 
                                != 0)) {
                        inputData = sStoreVariable;
                        fieldData = arrLines[iCount];
                        WriteToLookupFile();
                        resultFlag = "Pass";
                        break;
                    }
                    
                }
                
                filesys = null;
            }
            catch (Exception ex) {
                WriteConsoleLog(("VerifyDataInReport exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("VerifyDataInReport finished");
    }
    
    public final void SwitchTab() {
        WriteConsoleLog("SwitchTab started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                List<BrowserApplication> Browsers;
                Browsers = Silktest.FindAll(Of, BrowserApplication)["/BrowserApplication"];
                for (Browser : Browsers) {
                    int test;
                    test = inputdata;
                    // msgbox(test)
                    Browser.SelectTab(test).Click();
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("SwitchTab exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("SwitchTab finished");
    }
    
    public final void ConvertDate() {
        // IR-75273-001;AppDate;LONG;NewMonth
        // scenarioid;variable from lookupfile ;Case(Long or Short); variable to store value
        WriteConsoleLog("ConvertDate started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object modvalue;
                object varcase;
                object moddate;
                if (((inputdata.IndexOf(";") + 1) 
                            > 0)) {
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                    varcase = inputdataSplit(2);
                }
                else {
                    tempScriptID = scenarioID;
                    strVariable = inputdata;
                }
                
                retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, tempScriptID, strVariable);
                DateTime appldate;
                appldate = DateTime.Parse(retrieved_Data);
                varcase = varcase.ToUpper();
                switch (varcase) {
                    case "SHORT":
                        moddate = appldate.ToShortDateString;
                        moddate = moddate.Replace("-", "/");
                        // msgbox(moddate)
                        break;
                    case "LONG":
                        moddate = appldate.ToLongDateString;
                        moddate = moddate.Replace(" ", "/");
                        // msgbox(moddate)        
                        break;
                }
                scenarioID = tempScriptID;
                inputData = inputdataSplit(3);
                fieldData = moddate;
                WriteToLookupFile();
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("ConvertDate finished");
    }
    
    public final void Results() {
        WriteConsoleLog("Results started");
        System.IO.DirectoryInfo ResultPath = new System.IO.DirectoryInfo((path + "\\Result.csv"));
        object ObjFileSys;
        object ObjResult;
        ObjResult = objFSO.OpenTextFile(ResultPath, 8);
        if ((resultFlag == "Pass")) {
            ObjResult.WriteLine((scenarioID + ("," + resultFlag)));
            ObjResult.Close;
        }
        else {
            ObjResult.WriteLine((scenarioID + ("," 
                            + (resultFlag + ("," 
                            + (stepID + ("," 
                            + (actualMessage + ("," 
                            + (objectMapName + ("," 
                            + (fieldName + ("," + keyword)))))))))))));
            ObjResult.Close;
        }
        
        WriteConsoleLog("Results finished");
    }
    
    public final void ResetTeller(void UserId) {
        Process ps;
        for (ps : Process.GetProcesses) {
            if ((ps.ProcessName == "putty")) {
                ps.Kill;
            }
            
        }
        
        watch.Stop();
        double exetime;
        exetime = System.Math.Round(watch.Elapsed.TotalMinutes, 3);
        psi.UseShellExecute = true;
        psi.FileName = "C:\\SBI_SilkTest\\SBI_ARTP\\Vbs_Files\\ResetTellers.vbs";
        psi.Arguments = (UserId + (" " + outputFilePath));
        // Workbench.ResultComment(executionLogPath)
        Process.Start(psi);
        inputdata = 10;
        wait();
    }
    
    public final void ResetTellerWithLoggedInutty(void TellerID) {
        WriteConsoleLog("ResetTellerWithLoggedInutty started");
        object Tempinputdata;
        try {
            // Agent.DetachAll()
            Agent.Attach("putty.exe");
            silkTest.Window("PuttyResetTeller").SetActive();
            silkTest.Window("PuttyResetTeller").TypeKeys(TellerID);
            silkTest.Window("PuttyResetTeller").TypeKeys("<ENTER>");
            Wait(3);
            silkTest.Window("PuttyResetTeller").SetActive();
            silkTest.Window("PuttyResetTeller").TypeKeys(TellerID);
            silkTest.Window("PuttyResetTeller").TypeKeys("<ENTER>");
            WriteConsoleLog("Teller is now reset");
            Wait(3);
            silkTest.BrowserApplication("User Open").SetActive();
            silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomButton("Login").Click();
            //         Dim cnt As Integer = 1
            //         For cnt = 1 To 3
            //             WriteConsoleLog("ResetTellerWithLoggedInutty Checking the status again")
            //             If instr(silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("Message").GetText(),"MULTIPLE TELLER LOGIN NOT ALLOWED") > 0 Then
            //                 WriteConsoleLog("Teller is already logged in, trying to reset")
            //                 silkTest.Window("PuttyResetTeller").SetActive()
            //                 silkTest.Window("PuttyResetTeller").TypeKeys(TellerID)
            //                 silkTest.Window("PuttyResetTeller").TypeKeys("<ENTER>")
            //                 Wait(3)
            //                 WriteConsoleLog("Teller is now reset")
            //                 silkTest.BrowserApplication("User Open").SetActive()
            //                 silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomButton("Login").Click()                
            //             Else
            //                 Exit For
            //             End If
            //         Next        
        }
        catch (Exception ex) {
            WriteConsoleLog(("ResetTellerWithLoggedInutty exception generated. " + ex.Message));
            actualMessage = Err.Description;
            resultFlag = "Terminated";
            WriteToOutputFile();
        }
        
        WriteConsoleLog("ResetTellerWithLoggedInutty finished");
    }
    
    public final void CloseProcess(string ProcessName) {
        // KillProcess.vbs
        WriteConsoleLog("CloseProcess started");
        psi.FileName = (path + "\\Vbs_Files\\KillProcess.vbs");
        psi.Arguments = ProcessName;
        Workbench.ResultComment(psi.FileName);
        Process.Start(psi);
        WriteConsoleLog(("CloseProcess Killed Process " + ProcessName));
        WriteConsoleLog("CloseProcess finished");
    }
    
    public final void Inputlist() {
        WriteConsoleLog("Inputlist started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                //             
                if (inputdata.ToString.Contains("#")) {
                    if ((inputdata.ToString.Split("#").Length > 1)) {
                        object temptext = inputdata.ToString.Split("#")[1];
                        inputdata = temptext;
                    }
                    
                }
                
                string tempInputdata = "";
                listVal = "";
                switch (className) {
                    case "DomTextField":
                        WriteConsoleLog("Inputlist inside TextField");
                        inputdata = inputdata.Trim();
                        object DropdownAttrb = ("@Text=\'" 
                                    + (inputdata + "\'"));
                        if (((inputdata.IndexOf("<") + 1) 
                                    > 0)) {
                            tempInputdata = inputdata;
                            inputdata = inputdata.Replace("<", "<LessThan>");
                        }
                        else if (((inputdata.IndexOf(">") + 1) 
                                    > 0)) {
                            tempInputdata = inputdata;
                            inputdata = inputdata.Replace(">", "<GreaterThan>");
                        }
                        else {
                            // do nothing
                        }
                        
                        objValue.DomTextField(fieldName).Click();
                        object ExistingTimeOut = Agent.GetOption(Options.ObjectResolveTimeout);
                        Agent.SetOption(Options.ObjectResolveTimeout, 1000);
                        // Date: 10.02.2022
                        // Error Desc: Object Not found exception while identifying dropdown at line no 5135.
                        // Change Desc: To handle both the dropdown buttons in CBS
                        // Author: Ankush / Satish
                        if ((objValue.DomTextField(fieldName).GetDomAttribute("FNSType") == "fieldinput")) {
                            boolean x = false;
                            try {
                                DomElement AutoComplete = objValue.DomTextField(fieldName).Find(Of, DomElement)["/../*[@class=\'AutoComplete_image\' or @class=\'AutoComplete_button\']"];
                                AutoComplete.DomClick();
                            }
                            catch (System.Exception End) {
                                try {
                                }
                                
                                Agent.SetOption(Options.ObjectResolveTimeout, ExistingTimeOut);
                                //                     objValue.DomTextField(fieldName).SetText("")
                                objValue.DomTextField(fieldName).TypeKeys(inputdata);
                                if (((inputdata.IndexOf("\'") + 1) 
                                            == 0)) {
                                    // if the input data contains an apostrophe (else part) then just press the down key
                                    try {
                                        //                             Agent.SetOption("OPT_SYNC_TIMEOUT", 1)
                                        silkTest.BrowserApplication("BrowserApplication").BrowserWindow("BrowserWindow").DomLink(DropdownAttrb).Click();
                                        //                             Agent.SetOption("OPT_SYNC_TIMEOUT", 30000)
                                    }
                                    catch (Exception ex) {
                                        objValue.DomTextField(fieldName).SetText("");
                                        objValue.DomTextField(fieldName).TypeKeys(inputdata);
                                        silkTest.BrowserApplication("BrowserApplication").BrowserWindow("BrowserWindow").DomLink(DropdownAttrb).Click();
                                    }
                                    
                                }
                                else if ((((DropdownAttrb.IndexOf("<") + 1) 
                                            > 0) 
                                            || ((DropdownAttrb.IndexOf(">") + 1) 
                                            > 0))) {
                                    objValue.DomTextField(fieldName).TypeKeys("TAB");
                                }
                                else {
                                    objValue.DomTextField(fieldName).TypeKeys("<DOWN>");
                                    objValue.DomTextField(fieldName).TypeKeys("<TAB>");
                                }
                                
                                object newinputdata = objValue.DomTextField(fieldName).Text;
                                if ((tempInputdata.Trim() != "")) {
                                    inputdata = tempInputdata;
                                }
                                
                                if ((newinputdata.Trim() == inputdata.Trim())) {
                                    resultFlag = "Pass";
                                }
                                else {
                                    resultFlag = "Fail";
                                }
                                
                                "DomListBox";
                                object objAttribute = objValue.DomListBox(fieldName).GetDomAttribute("FNSType");
                                object objAttributeStrict = objValue.DomListBox(fieldName).GetDomAttribute("strict");
                                WriteConsoleLog("Inputlist :- Inside DomListBox FNSType");
                                //                     MsgBox(Ucase(CStr(objAttributeStrict)) & " " & Ucase(CStr(True)))
                                if (((objAttribute == "comboboxselect") 
                                            && (objAttributeStrict.ToString().ToUpper() == true.ToString().ToUpper()))) {
                                    WriteConsoleLog("Inputlist strict True");
                                    try {
                                        objValue.DomListBox(fieldName).PressKeys(inputdata);
                                        objValue.DomListBox(fieldName).PressKeys("<TAB>");
                                    }
                                    catch (Exception ex1) {
                                        if (((ex1.Message.IndexOf("not find") + 1) 
                                                    > 0)) {
                                            // Do nothing
                                        }
                                        
                                    }
                                    
                                }
                                else if ((objAttribute == "comboboxselect")) {
                                    WriteConsoleLog("Inputlist inside strict false and comboboxselect");
                                    // objValue.DomListBox(fieldName).TypeKeys("<Backspace>")
                                    listVal = objValue.DomListBox(fieldName).text;
                                    for (itr = 1; (itr <= 100); itr++) {
                                        if ((listVal.Trim() == inputdata.Trim())) {
                                            // Instr(listVal ,inputdata) > 0 And Instr(inputdata,listVal) >0 Then 'Fails here
                                            break;
                                        }
                                        
                                        objValue.DomListBox(fieldName).Click;
                                        objValue.DomListBox(fieldName).PressKeys("<Down>");
                                        objValue.DomListBox(fieldName).TypeKeys("<Enter>");
                                        // WriteConsoleLog(Trim(listVal))
                                        listVal = objValue.DomListBox(fieldName).text;
                                    }
                                    
                                }
                                else {
                                    WriteConsoleLog("Inputlist inside else");
                                    listVal = objValue.DomListBox(fieldName).text;
                                    objValue.DomListBox(fieldName).Click;
                                    objValue.DomListBox(fieldName).TypeKeys("<Backspace>");
                                    objValue.DomListBox(fieldName).Select(inputdata);
                                }
                                
                                object newlistVal = objValue.DomListBox(fieldName).text;
                                if ((newlistVal.ToUpper().Trim() == inputdata.ToUpper().Trim())) {
                                    resultFlag = "Pass";
                                }
                                else {
                                    resultFlag = "Fail";
                                    actualMessage = ("Expected Value in Dropdown is " 
                                                + (inputdata + (" and Actual data is " + newlistVal)));
                                }
                                
                            }
                            catch (Exception ex) {
                                WriteConsoleLog(("Inputlist exception generated. " + ex.Message));
                                actualMessage = Err.Description;
                                SetRecoveryAndResultFlag();
                                // recoveryFlag=1
                                WriteToOutputFile();
                            }
                            
                        }
                        
                        WriteConsoleLog("Inputlist finished");
                        break;
                }
            }
            
            InputListPressKeys();
            WriteConsoleLog("InputListPressKeys started");
            if (((resultFlag == "Pass") 
                        || (resultFlag == "Recover"))) {
                try {
                    // Date: 05.05.2022
                    // Error Desc: To handle dropdowns where enter key is not working for selection.
                    // Change Desc: Using Select function for selecting the first item
                    // Author: Ankush / Dhiraj
                    objValue.DomListBox(fieldName).Select(0);
                    objValue.DomListBox(fieldName).PressKeys(inputdata);
                    objValue.DomListBox(fieldName).PressKeys("<TAB>");
                    object newlistVal = objValue.DomListBox(fieldName).text;
                    if ((newlistVal.ToUpper().Trim() == inputdata.ToUpper().Trim())) {
                        resultFlag = "Pass";
                    }
                    else {
                        resultFlag = "Fail";
                        actualMessage = ("Expected Value in Dropdown is " 
                                    + (inputdata + (" and Actual data is " + newlistVal)));
                    }
                    
                }
                catch (Exception ex) {
                    WriteConsoleLog(("InputListPressKeys Exception generated " + ex.Message));
                    actualMessage = Err.Description;
                    SetRecoveryAndResultFlag();
                    // recoveryFlag=1
                    WriteToOutputFile();
                }
                
            }
            
            WriteConsoleLog("InputListPressKeys finished");
        }
        
    }
	public final void InputListDownArrow() {
        WriteConsoleLog("InputListDownArrow started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                objValue.DomListBox(fieldName).Click;
                objValue.DomListBox(fieldName).PressKeys("<Up>");
                objValue.DomListBox(fieldName).TypeKeys("<Enter>");
                objValue.DomListBox(fieldName).Click;
                objValue.DomListBox(fieldName).PressKeys("<Up>");
                objValue.DomListBox(fieldName).TypeKeys("<Enter>");
                string listVal = objValue.DomListBox(fieldName).text;
                // Date: 05.03.2022
                // Error Desc: To handle dropdowns with multiple items > 100 at line no 5287.
                // Change Desc: To handle both the dropdown multiple items
                // Author: Ankush / Dhiraj
                while ((listVal.Trim() != inputdata.Trim())) {
                    // WriteConsoleLog(Trim(listVal))
                    objValue.DomListBox(fieldName).Click;
                    objValue.DomListBox(fieldName).PressKeys("<Down>");
                    objValue.DomListBox(fieldName).TypeKeys("<Enter>");
                    // Wait(5)
                    listVal = objValue.DomListBox(fieldName).text;
                }
                
                // For itr=1 To 100
                //     If Trim(listVal) = Trim(inputdata) Then 'Instr(listVal ,inputdata) > 0 And Instr(inputdata,listVal) >0 Then 'Fails here
                //         Exit For
                //     End If                            
                //     objValue.DomListBox(fieldName).Click
                //     objValue.DomListBox(fieldName).PressKeys("<Down>")
                //     objValue.DomListBox(fieldName).TypeKeys("<Enter>")
                //     listVal=objValue.DomListBox(fieldName).text
                // Next
                listVal = objValue.DomListBox(fieldName).text;
                if ((listVal.ToUpper().Trim() == inputdata.ToUpper().Trim())) {
                    resultFlag = "Pass";
                }
                else {
                    resultFlag = "Fail";
                    actualMessage = ("Expected Value in Dropdown is " 
                                + (inputdata + (" and Actual data is " + listVal)));
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("InputListDownArrow Exception generated " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag = 1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("InputListDownArrow finished");
    }
    
    public final void CloseOpenDialogsOrWindows() {
        WriteConsoleLog("Inside CloseOpenDialogsOrWindows");
        // Checking for dialogs
        try {
            WriteConsoleLog("Checking whether dialog exist.");
            if (silkTest.Dialog("@caption=\'*\'").Exists()) {
                WriteConsoleLog(("Dialog with caption " 
                                + (silkTest.Dialog("@caption=\'*\'").GetProperty("caption") + " exist.")));
                silkTest.Dialog("@caption=\'*\'").PushButton("@caption=\'*\'").Click();
                WriteConsoleLog("Dialog button clicked.");
            }
            
        }
        catch (Exception ex) {
            WriteConsoleLog(("Open Dialog does no exist. " 
                            + (ex.Message + (" ." + Err.Description))));
        }
        
        // Checking for Windows
        try {
            object WindowsFound = false;
            WriteConsoleLog("Checking whether Window exist.");
            if (silkTest.BrowserApplication("@caption=\'*\'").Window("@caption=\'*\'").Exists()) {
                object WindowCaption = silkTest.BrowserApplication("@caption=\'*\'").Window("@caption=\'*\'").GetProperty("caption");
                if (((WindowCaption.ToUpper().IndexOf("TELLER DETAILS FOR FORGOT PASSWORD") + 1) 
                            > 0)) {
                    WriteConsoleLog(("Window with caption " 
                                    + (WindowCaption + " exist.")));
                    silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip").DomClick();
                    WindowsFound = true;
                }
                
                if (((WindowCaption.ToUpper().IndexOf("CASH DRAWER") + 1) 
                            > 0)) {
                    WriteConsoleLog(("Window with caption " 
                                    + (WindowCaption + " exist.")));
                    silkTest.Window("CashDrawerClose").BrowserWindow("BrowserWindow").DomButton("Close").DomClick();
                    DynamicParentWait(silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip"));
                    silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip").DomClick();
                    WindowsFound = true;
                }
                
                if (((WindowCaption.ToUpper().IndexOf("CALENDAR") + 1) 
                            > 0)) {
                    WriteConsoleLog(("Window with caption " 
                                    + (WindowCaption + " exist.")));
                    silkTest.Window("CashDrawerClose").BrowserWindow("BrowserWindow").DomButton("Close").DomClick();
                    WindowsFound = true;
                }
                
                if ((WindowsFound == false)) {
                    WriteConsoleLog(("Window with caption " 
                                    + (WindowCaption + " exist.")));
                    silkTest.Window("CashDrawerClose").BrowserWindow("BrowserWindow").Close;
                    WriteConsoleLog("Closed Window");
                }
                
            }
            
        }
        catch (Exception e) {
            WriteConsoleLog(("Open Window does no exist. " 
                            + (e.Message + (" ." + Err.Description))));
        }
        
        WriteConsoleLog("Exiting CloseOpenDialogsOrWindows");
    }
    
    public final void CloseApp() {
        try {
            // CloseOpenDialogsOrWindows()
            WriteConsoleLog("Inside Close App");
            object TabCount = silkTest.BrowserApplication("BrowserApplication").GetTabCount;
            object i = 1;
            WriteConsoleLog(("Tab Count : " + TabCount));
            object TabClosed = false;
            for (i = 1; (i <= TabCount); i++) {
                try {
                    TabClosed = false;
                    try {
                        silkTest.BrowserApplication("User Open").SetActive;
                        Wait(5);
                        DynamicParentWait(silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("SignOff"));
                        silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("SignOff").Click();
                        WriteConsoleLog("SignOff Link exists and clicked");
                        Wait(5);
                    }
                    catch (Exception ex1) {
                        if (((ex1.Message.IndexOf("not find") + 1) 
                                    > 0)) {
                            WriteConsoleLog("Sign Off link not found. Closing Tab");
                            silkTest.BrowserApplication("User Open").CloseTab(i);
                            TabClosed = true;
                        }
                        
                    }
                    
                    object DoNotExitLoopCnt = 0;
                    while ((TabClosed == false)) {
                        try {
                            Wait(5);
                            DynamicParentWait(silkTest.BrowserApplication("User Open").Dialog("Dialog"));
                            object MsgDialog = silkTest.BrowserApplication("User Open").Dialog("Dialog").Label("Label Msg").GetProperty("Text");
                            //                     If MsgDialog <> "Closing The Window For Security Reasons"
                            //                         silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("SignOff").DomClick()
                            //                     End If    
                            //                                 
                            WriteConsoleLog(("Dialog Text " + MsgDialog));
                            //                     silkTest.BrowserApplication("User Open").SelectTab(i)
                            silkTest.BrowserApplication("User Open").Dialog("Dialog").PushButton("@caption=\'*\'").Click();
                            Wait(5);
                            if (((MsgDialog.ToUpper().IndexOf("DO YOU WANT TO CLOSE THIS TAB") + 1) 
                                        > 0)) {
                                TabClosed = true;
                                WriteConsoleLog(("Tab Closed " + i));
                                Wait(7);
                                break;
                            }
                            
                        }
                        catch (Exception ex) {
                            if (((ex.Message.IndexOf("not find") + 1) 
                                        > 0)) {
                                WriteConsoleLog(("Object not found yet " + DoNotExitLoopCnt));
                            }
                            
                        }
                        finally {
                            DoNotExitLoopCnt = (DoNotExitLoopCnt + 1);
                            if ((DoNotExitLoopCnt == 20)) {
                                WriteConsoleLog("Max iteration count exceeded exiting the while loop");
                                TabClosed = true;
                                DoNotExitLoopCnt = 0;
                            }
                            
                        }
                        
                    }
                    
                }
                catch (Exception ex1) {
                    WriteConsoleLog(("Exception generated in CloseApp " 
                                    + (ex1.Message + (" " + Err.Description))));
                }
                
            }
            
        }
        catch (Exception ex) {
            WriteConsoleLog(("Exception generated in Inside Close App calling CloseOpenDialogsOrWindows " 
                            + (ex.Message + (" . " + Err.Description))));
            CloseOpenDialogsOrWindows();
            //     Call CloseApp()
        }
        
        WriteConsoleLog("Exiting Close App");
    }
    
    public final void CloseAppOld() {
        WriteConsoleLog("CloseApp started");
        try {
            object ExitWhile = 0;
            if (silkTest.BrowserApplication("User Open").Enabled) {
                // If any dialog box is not open
                WriteConsoleLog("CloseApp Application Enabled = True");
                object TabCount = silkTest.BrowserApplication("BrowserApplication").GetTabCount;
                object TabClosed = false;
                for (i = 1; (i <= TabCount); i++) {
                    WriteConsoleLog(("CloseApp Application Inside for Loop. Total Tab count " + TabCount));
                    TabClosed = false;
                    while ((TabClosed == false)) {
                        WriteConsoleLog(("CloseApp Application Enabled = True. ExitWhile :- " + ExitWhile));
                        ExitWhile = (ExitWhile + 1);
                        // if any home page with a Signoff button exists
                        int cnt1 = 1;
                        for (cnt1 = 1; (cnt1 <= 10); cnt1++) {
                            WriteConsoleLog(("Waiting for signoff button to be active " + cnt1));
                            try {
                                DynamicParentWait(silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("SignOff"));
                                break;
                            }
                            catch (Exception ex) {
                                Wait(1);
                            }
                            
                        }
                        
                        try {
                            WriteConsoleLog(("CloseApp Application if any home page with a Signoff button exists. ExitWhile :- " + ExitWhile));
                            if ((silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("SignOff").Exists() && silkTest.BrowserApplication("User Open").Enabled)) {
                                WriteConsoleLog(("CloseApp Application home page with a Signoff button exists. ExitWhile :- " + ExitWhile));
                                silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("SignOff").DomClick();
                            }
                            
                        }
                        catch (Exception ex) {
                            //                     MsgBox(ex.Message)
                            WriteConsoleLog(("CloseApp Application Exception generated if any home page with a Signoff button exists. ExitWhile :- " +
                                "" 
                                            + (ExitWhile + (" " + ex.Message))));
                            if (((ex.Message.IndexOf("not find") + 1) 
                                        > 0)) {
                                // Do nothing
                            }
                            
                        }
                        
                        if ((TabClosed == false)) {
                            // Happy Signout Path Step 2- Handle popups if present
                            try {
                                WriteConsoleLog(("CloseApp Application Happy Signout Path Step 2 checking dialog presence. ExitWhile :- " + ExitWhile));
                                if (silkTest.BrowserApplication("User Open").Dialog("Dialog").Exists()) {
                                    WriteConsoleLog(("CloseApp Application Happy Signout Path Step 2 checking dialog is present. ExitWhile :- " + ExitWhile));
                                    HandlePopUpBox();
                                    TabClosed = true;
                                }
                                
                            }
                            catch (Exception ex) {
                                WriteConsoleLog(("CloseApp Application Exception generated. Happy Signout Path Step 2 checking dialog is present. ExitW" +
                                    "hile :- " 
                                                + (ExitWhile + (" " + ex.Message))));
                                if (((ex.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // Do nothing
                                }
                                
                            }
                            
                        }
                        
                        if ((TabClosed == false)) {
                            // if login page exists close that tab
                            try {
                                WriteConsoleLog(("CloseApp Application if login page exists close that tab. ExitWhile :- " + ExitWhile));
                                if (silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomTextField("User No").Exists()) {
                                    WriteConsoleLog(("CloseApp Application login page exists closing that tab. ExitWhile :- " + ExitWhile));
                                    silkTest.BrowserApplication("User Open").CloseTab();
                                    TabClosed = true;
                                }
                                
                            }
                            catch (Exception ex) {
                                WriteConsoleLog(("CloseApp Application Exception generated. login page exists closing that tab. ExitWhile :- " 
                                                + (ExitWhile + (" " + ex.Message))));
                                if (((ex.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // Do nothing
                                }
                                
                            }
                            
                        }
                        
                        if ((TabClosed == false)) {
                            // checking whether CloseCashdrawer present
                            try {
                                WriteConsoleLog(("CloseApp Application checking whether CloseCashdrawer is present. ExitWhile :- " + ExitWhile));
                                if (silkTest.Window("CashDrawerClose").BrowserWindow("BrowserWindow").DomButton("Close").Exists()) {
                                    WriteConsoleLog(("CloseApp Application CloseCashdrawer is present. ExitWhile :- " + ExitWhile));
                                    silkTest.Window("CashDrawerClose").BrowserWindow("BrowserWindow").DomButton("Close").Click();
                                    WriteConsoleLog(("CloseApp Application CloseCashdrawer is present. Clicked Close. ExitWhile :- " + ExitWhile));
                                }
                                
                            }
                            catch (Exception ex) {
                                WriteConsoleLog(("CloseApp Application Exception generated CloseCashdrawer is present. ExitWhile :- " 
                                                + (ExitWhile + (" " + ex.Message))));
                                if (((ex.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // Do nothing
                                }
                                
                            }
                            
                        }
                        
                        if ((TabClosed == false)) {
                            // checking whether loginSkip present
                            try {
                                WriteConsoleLog(("CloseApp Application checking whether loginSkip present. ExitWhile :- " + ExitWhile));
                                if (silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip").Exists()) {
                                    WriteConsoleLog(("CloseApp Application loginSkip is present. ExitWhile :- " + ExitWhile));
                                    silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip").Click();
                                    WriteConsoleLog(("CloseApp Application loginSkip clicked. ExitWhile :- " + ExitWhile));
                                }
                                
                            }
                            catch (Exception ex) {
                                WriteConsoleLog(("CloseApp Application Exception generated checking loginSkip present. ExitWhile :- " 
                                                + (ExitWhile + (" " + ex.Message))));
                                if (((ex.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // Do nothing
                                }
                                
                            }
                            
                        }
                        
                        if ((TabClosed == false)) {
                            // check if any WindowDialog is present                                                
                            try {
                                WriteConsoleLog(("CloseApp Application checking whether any WindowDialog is present. ExitWhile :- " + ExitWhile));
                                if (silkTest.Window("@caption=\'*\'").Exists()) {
                                    WriteConsoleLog(("CloseApp Application WindowDialog is present. ExitWhile :- " + ExitWhile));
                                    silkTest.Window("@caption=\'*\'").Close();
                                    WriteConsoleLog(("CloseApp Application WindowDialog closed. ExitWhile :- " + ExitWhile));
                                }
                                
                            }
                            catch (Exception e) {
                                ExitWhile = (ExitWhile + 1);
                                WriteConsoleLog(("CloseApp Application Exception generated WindowDialog is present. ExitWhile :- " 
                                                + (ExitWhile + (" " + e.Message))));
                            }
                            
                            // checking whether any Window popup is present like Date Calendar
                            try {
                                WriteConsoleLog(("CloseApp Application checking whether Calendar Dialog is present. ExitWhile :- " + ExitWhile));
                                if (silkTest.Window("CalendarWebpage Dialog").BrowserWindow("BrowserWindow").DomButton("Close").Exists()) {
                                    WriteConsoleLog(("CloseApp Application Calendar Dialog is present. ExitWhile :- " + ExitWhile));
                                    silkTest.Window("CalendarWebpage Dialog").BrowserWindow("BrowserWindow").Close();
                                    WriteConsoleLog(("CloseApp Application Calendar Dialog closed. ExitWhile :- " + ExitWhile));
                                }
                                
                            }
                            catch (Exception ex) {
                                WriteConsoleLog(("CloseApp Application Exception generated Calendar Dialog is present. ExitWhile :- " 
                                                + (ExitWhile + (" " + ex.Message))));
                                if (((ex.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // Do nothing
                                }
                                
                            }
                            
                        }
                        
                        // checking ExitWhile Count
                        WriteConsoleLog(("CloseApp Application Checking ExitWhile count :- " + ExitWhile));
                        if ((ExitWhile == 7)) {
                            // to avoid infinite while loop
                            WriteConsoleLog(("CloseApp Application ExitWhile count greater than 7 Killing iexplore.exe:- " + ExitWhile));
                            CloseProcess("iexplore.exe");
                            //                         silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").Close
                            TabClosed = true;
                        }
                        
                        Wait(1);
                    }
                    
                }
                
            }
            else {
                WriteConsoleLog("CloseApp Application Enabled = False");
                HandleModalWindows();
                HandlePopUpBox();
                CloseApp();
            }
            
        }
        catch (Exception ex) {
            WriteConsoleLog(("CloseApp exception generated. " + ex.Message));
            actualMessage = Err.Description;
            SetRecoveryAndResultFlag();
            // recoveryFlag=1
            WriteToOutputFile();
        }
        
        WriteConsoleLog("CloseApp Finished");
    }
    
    public final void HandleModalWindows() {
        object ExitWhile;
        ExitWhile = 1;
        // checking whether any Window popup is present like Date Calendar
        try {
            WriteConsoleLog("HandleModalWindows checking whether Calendar Dialog is present.");
            if (silkTest.Window("CalendarWebpage Dialog").BrowserWindow("BrowserWindow").DomButton("Close").Exists()) {
                WriteConsoleLog("HandleModalWindows Calendar Dialog is present.");
                silkTest.Window("CalendarWebpage Dialog").BrowserWindow("BrowserWindow").Close();
                WriteConsoleLog("HandleModalWindows Calendar Dialog closed.");
            }
            
        }
        catch (Exception ex) {
            WriteConsoleLog(("HandleModalWindows Exception generated Calendar Dialog is present" + (" " + ex.Message)));
            if (((ex.Message.IndexOf("not find") + 1) 
                        > 0)) {
                // Do nothing
            }
            
        }
        
        try {
            WriteConsoleLog("HandleModalWindows checking whether loginSkip present.");
            if (silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip").Exists()) {
                WriteConsoleLog("HandleModalWindows loginSkip is present.");
                silkTest.Window("LoginSkip").BrowserWindow("BrowserWindow").DomButton("btnSkip").Click();
                WriteConsoleLog("HandleModalWindows loginSkip clicked.");
            }
            
        }
        catch (Exception ex) {
            WriteConsoleLog(("HandleModalWindows Exception generated checking loginSkip present. " + ex.Message));
            if (((ex.Message.IndexOf("not find") + 1) 
                        > 0)) {
                // Do nothing
            }
            
        }
        
    }
    
    public final void HandlePopUpBox() {
        WriteConsoleLog("HandlePopUpBox Started");
        try {
            Wait(1);
            while (silkTest.BrowserApplication("User Open").Dialog("Dialog").Exists()) {
                try {
                    if (silkTest.BrowserApplication("User Open").Dialog("Dialog").PushButton("OK").Exists()) {
                        silkTest.BrowserApplication("User Open").Dialog("Dialog").PushButton("OK").Click();
                    }
                    
                }
                catch (Exception ex) {
                    if (((ex.Message.IndexOf("not find") + 1) 
                                > 0)) {
                        // Do nothing
                    }
                    
                }
                
                // Yes No Button            
                try {
                    if (silkTest.BrowserApplication("User Open").Dialog("Dialog").PushButton("Yes").Exists()) {
                        silkTest.BrowserApplication("User Open").Dialog("Dialog").PushButton("Yes").Click();
                    }
                    
                }
                catch (Exception ex) {
                    if (((ex.Message.IndexOf("not find") + 1) 
                                > 0)) {
                        // Do nothing
                    }
                    
                }
                
                // Wait(1)
            }
            
        }
        catch (Exception ex) {
            WriteConsoleLog(("HandlePopUpBox exception generated ." + ex.Message));
            if (((ex.Message.IndexOf("not find") + 1) 
                        > 0)) {
                // Do nothing
            }
            
        }
        
        WriteConsoleLog("HandlePopUpBox Finshed");
    }
    
    public final void DateInput() {
        // Generic Function to input Date -data should be in format 10/12/2018
        // FIX 08 and 8 identification in DD
        WriteConsoleLog("DateInput Started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            if (((inputdata.IndexOf("-") + 1) 
                        > 0)) {
                inputdata = inputdata.Replace("-", "/");
            }
            
            try {
                if (((inputdata.IndexOf(";") + 1) 
                            == 0)) {
                    WriteConsoleLog("Found hardcoded date");
                    inputdataSplit = inputdata.Split("/");
                    // can we come out if else
                }
                else {
                    WriteConsoleLog("Found ; in inputdata searching for a variable in lookup");
                    inputdataSplit = inputdata.Split(";");
                    tempScriptID = inputdataSplit(0);
                    strVariable = inputdataSplit(1);
                    WriteConsoleLog(("Found ; in inputdata searching for " 
                                    + (strVariable + " variable in lookup")));
                    if ((tempScriptID == "EOM")) {
                        // to get ServerDate's last day of the month EOM;ServerEOM 
                        WriteConsoleLog("EOM found Calculating End of Month");
                        object ServerDate = silkTest.BrowserApplication("User Open").BrowserWindow("BrowserWindow").DomElement("ServerDate").GetText();
                        object arrServerDate = ServerDate.Split("/");
                        //                 msgbox (System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern)
                        object getDate = DateSerial(arrServerDate[2], (int.Parse(arrServerDate[1]) + 1), 0).ToString();
                        if (((getDate.IndexOf("-") + 1) 
                                    > 0)) {
                            getDate = getDate.Replace("-", "/");
                        }
                        
                        object arr = getDate.Split("/");
                        object newDate;
                        if ((System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern == "dd-mm-yyyy")) {
                            newDate = (arr[0] + ("/" 
                                        + (arr[1] + ("/" + arr[2]))));
                        }
                        else if ((System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern == "MM-DD-YYYY")) {
                            newDate = (arr[1] + ("/" 
                                        + (arr[0] + ("/" + arr[2]))));
                        }
                        else if ((System.Globalization.DateTimeFormatInfo.CurrentInfo.ShortDatePattern == "M/d/yyyy")) {
                            newDate = (arr[1] + ("/" 
                                        + (arr[0] + ("/" + arr[2]))));
                        }
                        else {
                            // add new formats
                        }
                        
                        fieldData = newDate;
                        inputdata = strVariable;
                        // IR-75367-001    CIF    85001437527
                        WriteToLookupFile();
                    }
                    
                    retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, scenarioID, strVariable);
                    WriteConsoleLog(("retrieved_Data : " + retrieved_Data));
                    inputdata = retrieved_Data;
                    inputdataSplit = inputdata.Split("/");
                    //                 MsgBox (inputdata)
                }
                
                string d_day = inputdataSplit(0);
                string m_month = inputdataSplit(1);
                string y_year = inputdataSplit(2);
                string d_day1;
                object mname;
                object d1_date = d_day.ToString.Length;
                object m1_month = m_month.ToString.Length;
                if ((d1_date == 1)) {
                    d_day1 = ("0" + d_day);
                }
                
                if ((m1_month == 1)) {
                    m_month = ("0" + m_month);
                    // msgbox(m_month)
                }
                
                object objAttribute;
                object listValue;
                objAttribute = objValue.DomElement(fieldName).GetProperty("class");
                WriteConsoleLog(("objAttribute : " + objAttribute));
                WriteConsoleLog(("d_day : " + d_day));
                switch (objAttribute) {
                    case "calendar_imageBttn":
                        WriteConsoleLog("Inside calendar_imageBttn Case");
                        objValue.DomElement(fieldName).DomClick;
                        object objcalender = silkTest.BrowserApplication(objectMapName).BrowserWindow("BrowserWindow");
                        object attrimonth = "@class=\'ui-datepicker-month\'";
                        object attriyear = "@class=\'ui-datepicker-year\'";
                        DynamicParentWait(objcalender.DomListBox(attrimonth));
                        objcalender.DomListBox(attrimonth).Select(0);
                        listValue = objcalender.DomListBox(attrimonth).text;
                        if ((listValue == "January")) {
                            mname = monthname(m_month);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        else if ((listValue == "Jan")) {
                            mname = monthname(m_month, true);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        
                        DynamicParentWait(objcalender.DomListBox(attriyear));
                        objcalender.DomListBox(attriyear).Select(y_year);
                        if (((int.Parse(d_day) > 0) 
                                    && (int.Parse(d_day) < 10))) {
                            // added to handle 01 and 1 in DD whichever is present it should click it
                            try {
                                DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                    + (int.Parse(d_day).ToString() + "\'"))));
                                objcalender.DomElement(("@textContents=\'" 
                                                + (int.Parse(d_day).ToString() + "\' and @class=\'ui-state-default*\'"))).Click;
                                // Click single digit Number converted to string i.e. instead of 01 click on 1
                            }
                            catch (Exception ex1) {
                                if (((ex1.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // if not found then append 0 and then click                            
                                    DynamicParentWait(objcalender.DomElement(("@textContents=\'0" 
                                                        + (d_day + "\' and @class=\'ui-state-default*\'"))));
                                    objcalender.DomElement(("@textContents=\'0" 
                                                    + (d_day + "\' and @class=\'ui-state-default*\'"))).Click;
                                }
                                
                            }
                            
                        }
                        else {
                            DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                + (d_day + "\' and @class=\'ui-state-default*\'"))));
                            objcalender.DomElement(("@textContents=\'" 
                                            + (d_day + "\' and @class=\'ui-state-default*\'"))).Click;
                        }
                        
                        break;
                    case "calendar_imageBttn NullValue":
                        WriteConsoleLog("calendar_imageBttn NullValue");
                        objValue.DomElement(fieldName).Click;
                        object objcalender = silkTest.BrowserApplication(objectMapName).BrowserWindow("BrowserWindow");
                        // objvalue
                        object attrimonth = "@class=\'ui-datepicker-month\'";
                        object attriyear = "@class=\'ui-datepicker-year\'";
                        DynamicParentWait(objcalender.DomListBox(attrimonth));
                        objcalender.DomListBox(attrimonth).Select(0);
                        listValue = objcalender.DomListBox(attrimonth).text;
                        if ((listValue == "January")) {
                            mname = monthname(m_month);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        else if ((listValue == "Jan")) {
                            mname = monthname(m_month, true);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        
                        DynamicParentWait(objcalender.DomListBox(attriyear));
                        objcalender.DomListBox(attriyear).Select(y_year);
                        if (((int.Parse(d_day) > 0) 
                                    && (int.Parse(d_day) < 10))) {
                            // added to handle 01 and 1 in DD whichever is present it should click it
                            try {
                                DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                    + (int.Parse(d_day).ToString() + "\' and @class=\'ui-state-default*\'"))));
                                objcalender.DomElement(("@textContents=\'" 
                                                + (int.Parse(d_day).ToString() + "\' and @class=\'ui-state-default*\'"))).DomClick;
                                // Click single digit Number converted to string i.e. instead of 01 click on 1
                            }
                            catch (Exception ex1) {
                                if (((ex1.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // if not found then append 0 and then click
                                    DynamicParentWait(objcalender.DomElement(("@textContents=\'0" 
                                                        + (d_day + "\' and @class=\'ui-state-default*\'"))));
                                    objcalender.DomElement(("@textContents=\'0" 
                                                    + (d_day + "\' and @class=\'ui-state-default*\'"))).DomClick;
                                }
                                
                            }
                            
                        }
                        else {
                            DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                + (d_day + "\' and @class=\'ui-state-default*\'"))));
                            objcalender.DomElement(("@textContents=\'" 
                                            + (d_day + "\' and @class=\'ui-state-default*\'"))).DomClick;
                        }
                        
                        // Date: 11.03.2022
                        // Error Desc: Screen 070713 - Unable to select Date of Bith.
                        // Change Desc: Added button_disableClass in the case statement.
                        // Author: Ankush / sIDDHESH
                        break;
                    case "button":
                    case "button disableClass":
                    case "DisableOnEnquiry":
                        WriteConsoleLog("button");
                        objValue.DomElement(fieldName).Click;
                        //                     Wait(2)
                        //                     Call DynamicWait(silkTest.BrowserApplication(objectMapName).Window("@caption='Calendar*'"))
                        object objcalender = silkTest.BrowserApplication(objectMapName).Window("@caption=\'Calendar -- Webpage Dialog\'").BrowserWindow("@windowClassName=\'Internet Explorer_Server\'");
                        //                     Dim objcalender = silkTest.BrowserApplication(objectMapName).Window("@windowClassName=Internet Explorer_TridentDlgFrame'").BrowserWindow("@windowClassName='Internet Explorer_Server'")
                        //                     //BrowserApplication//Window//BrowserWindow//Select[@id='month']
                        // objvalue
                        object attrimonth = "@id=\'month\'";
                        object attriyear = "@id=\'year\'";
                        DynamicParentWait(objcalender.DomListBox(attrimonth));
                        objcalender.DomListBox(attrimonth).Select(0);
                        listValue = objcalender.DomListBox(attrimonth).text;
                        if ((listValue == "January")) {
                            mname = monthname(m_month);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        else if ((listValue == "Jan")) {
                            mname = monthname(m_month, true);
                            objcalender.DomListBox(attrimonth).Select(mname);
                        }
                        
                        DynamicParentWait(objcalender.DomListBox(attriyear));
                        objcalender.DomListBox(attriyear).Select(y_year);
                        d_day = int.Parse(d_day);
                        if (((int.Parse(d_day) > 0) 
                                    && (int.Parse(d_day) < 10))) {
                            // added to handle 01 and 1 in DD whichever is present it should click it
                            try {
                                DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                    + (int.Parse(d_day).ToString() + "\'"))));
                                objcalender.DomElement(("@textContents=\'" 
                                                + (int.Parse(d_day).ToString() + "\'"))).Click;
                                // Click single digit Number converted to string i.e. instead of 01 click on 1
                            }
                            catch (Exception ex1) {
                                if (((ex1.Message.IndexOf("not find") + 1) 
                                            > 0)) {
                                    // if not found then append 0 and then click
                                    DynamicParentWait(objcalender.DomElement(("@textContents=\'0" 
                                                        + (d_day + "\'"))));
                                    objcalender.DomElement(("@textContents=\'0" 
                                                    + (d_day + "\'"))).Click;
                                }
                                
                            }
                            
                        }
                        else {
                            DynamicParentWait(objcalender.DomElement(("@textContents=\'" 
                                                + (d_day + "\'"))));
                            objcalender.DomElement(("@textContents=\'" 
                                            + (d_day + "\'"))).Click;
                        }
                        
                        break;
                }
            }
            catch (Exception ex) {
                WriteConsoleLog(("DateInput exception generated " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("DateInput Finished");
    }
    
    public final void RANDOMCORPIDNUM() {
        WriteConsoleLog("RANDOMCORPIDNUM started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            Random rmd = new Random();
            int rmd_number;
            string CorpIDPart1 = "";
            string CorpIDPart2 = "";
            string CorpIDPart3 = "PLC";
            CorpIDPart1 = (CorpIDPart1 + rmd.next(9999, 99999).ToString());
            CorpIDPart2 = (CorpIDPart2 + rmd.next(99999, 999999).ToString());
            inputdata = ("L" 
                        + (CorpIDPart1 + ("MH" 
                        + (Now.Year.ToString() 
                        + (CorpIDPart3 + CorpIDPart2)))));
            try {
                switch (className) {
                    case "DomTextField":
                        fielddata = objValue.DomTextField(fieldName).Text;
                        if ((fielddata == "")) {
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        else {
                            objValue.DomTextField(fieldName).SetText("");
                            objValue.DomTextField(fieldName).SetText(inputdata);
                        }
                        
                        break;
                }
                object newInputData = objValue.DomTextField(fieldName).Text.Trim();
                if ((newInputData == "")) {
                    resultFlag = "Fail";
                }
                else {
                    resultFlag = "Pass";
                }
                
            }
            catch (Exception ex) {
                WriteConsoleLog(("RANDOMCORPIDNUM exception generated : " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("RANDOMCORPIDNUM finsihed");
    }
    
    public final void DynamicObjectWait() {
        WriteConsoleLog("DynamicObjectWait Started");
        if ((ConditionalFlag == true)) {
            maxWaitInSeconds = 3;
        }
        else {
            maxWaitInSeconds = 10;
        }
        
        int secondCount;
        int maxSecond;
        secondCount = 1;
        // If inputdata <> "Filed dosenot Exist" Then
        for (secondCount = 1; (secondCount <= maxWaitInSeconds); secondCount++) {
            WriteConsoleLog(("Waiting for " 
                            + (fieldName + (". Attempt " + secondCount))));
            try {
                if (objValue.TestObject(fieldName).Exists()) {
                    //                 objValue.TestObject(fieldName).HighlightObject(500,Color.BLUE)
                    WriteConsoleLog("Object Found");
                    break;
                }
                
            }
            catch (Exception ex1) {
                if (((ex1.Message.IndexOf("not find") + 1) 
                            > 0)) {
                    Wait(1);
                }
                else {
                    WriteConsoleLog(("DynamicObjectWait exception generated : " + ex1.Message));
                    actualMessage = Err.Description;
                    resultFlag = "Terminated";
                    break;
                }
                
            }
            
        }
        
        // End If
        WriteConsoleLog("DynamicObjectWait Finished");
    }
    
    public final void DynamicParentWait(TestObject TestObj) {
        WriteConsoleLog("DynamicParentWait Started");
        int secondCount;
        int maxSecond;
        // MsgBox (ConditionalFlag)
        // If inputdata <> "Filed dosenot Exist" Then
        if ((ConditionalFlag == true)) {
            maxWaitInSeconds = 3;
        }
        else {
            maxWaitInSeconds = 20;
        }
        
        secondCount = 1;
        for (secondCount = 1; (secondCount <= maxWaitInSeconds); secondCount++) {
            WriteConsoleLog(("Waiting for " 
                            + (TestObj.ToString + (". Attempt " + secondCount))));
            try {
                if (TestObj.Exists()) {
                    WriteConsoleLog("Object Found");
                    break;
                }
                
            }
            catch (Exception ex1) {
                if (((ex1.Message.IndexOf("not find") + 1) 
                            > 0)) {
                    Wait(1);
                }
                else {
                    WriteConsoleLog(("DynamicParentWait exception generated : " + ex1.Message));
                    actualMessage = Err.Description;
                    resultFlag = "Terminated";
                    break;
                }
                
            }
            
        }
        
        // End If
        WriteConsoleLog("DynamicParentWait Finished");
    }
    
    public final void ClearBrowserCache() {
        WriteConsoleLog("ClearBrowserCache started");
        try {
            List<BrowserApplication> Browsers;
            Browsers = Silktest.FindAll(Of, BrowserApplication)["/BrowserApplication"];
            Browsers[0].ClearCache(0);
        }
        catch (Exception ex) {
            // Do nothing
        }
        
        WriteConsoleLog("ClearBrowserCache finished");
    }
    
    public final void WriteConsoleLog(string str) {
        object txtToWrite;
        //     DebugLogFile = DebugLogFSO.OpenTextFile(DebugLogFilePath,8)
        if (((str.IndexOf(" execution") + 1) 
                    > 0)) {
            txtToWrite = (Now.ToString() + ('\t' + str));
        }
        else {
            if (((str.ToUpper().IndexOf("EXCEPTION") + 1) 
                        > 0)) {
                str = ("***************" 
                            + (str + "***************"));
            }
            
            txtToWrite = (Now.ToString() + ("||" 
                        + (scenarioID + ("||" 
                        + (stepID + ("||" 
                        + (keyword + ("||" 
                        + (fieldName + ("||" 
                        + (inputdata + ("||" + str))))))))))));
        }
        
        //     Dim DebugLogFSO1 =createobject("Scripting.FileSystemObject")
        //     DebugLogFile = DebugLogFSO.OpenTextFile(DebugLogFilePath,8)    
        Console.WriteLine(txtToWrite);
        DebugLogFile.WriteLine(txtToWrite);
        //     DebugLogFile.Close()
    }
    
    public final void DeleteDLPTemp() {
        WriteConsoleLog("DeleteDLPTemp started");
        object DLPTempFSO;
        object DLPTempFolder;
        try {
            DLPTempFSO = createobject("Scripting.FileSystemObject");
            DLPTempFSO.DeleteFolder(("C:\\Users\\" 
                            + (Environment.UserName + "\\AppData\\LocalLow\\DLPTemp")), true);
        }
        catch (Exception ex) {
            // do nothing
        }
        
        WriteConsoleLog("DeleteDLPTemp finished");
    }
    
    public final void SetRecoveryAndResultFlag() {
        string ConsoleProp;
        WriteConsoleLog(("************ Error occurred in " 
                        + (stepID + (" " 
                        + (actualMessage + "************")))));
        if ((recoveryFlag == 0)) {
            // by default
            resultFlag = "Recover";
            recoveryFlag = 1;
            // 1 means Recovery is in progress, if script still fails when recovery flag is on then set the status as Terminated
            WriteConsoleLog(("************Attempting to recover " 
                            + (stepID + "************")));
        }
        else {
            WriteConsoleLog(("************Recovery for " 
                            + (stepID + " failed************")));
            ExtractAndLogProperties();
            resultFlag = "Terminated";
        }
        
    }
    
    public final void VerifyFieldProperty() {
        // Finds attribute value 
        // eg: disabled:Yes
        WriteConsoleLog("VerifyFieldProperty started");
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object attributename;
                object Expectedattributevalue;
                object Actualattributevalue;
                Array tableArgs = inputdata.Split(":");
                attributename = tableArgs[0];
                Expectedattributevalue = tableArgs[1].ToUpper();
                switch (className) {
                    case "DomTextField":
                        DynamicObjectWait();
                        Actualattributevalue = objValue.DomTextField(fieldName).GetDomAttribute(attributename).ToString.ToUpper();
                        break;
                    case "DomCheckBox":
                        DynamicObjectWait();
                        Actualattributevalue = objValue.DomCheckBox(fieldName).GetDomAttribute(attributename).ToString.ToUpper();
                        break;
                    case "DomListBox":
                        DynamicObjectWait();
                        Actualattributevalue = objValue.DomListBox(fieldName).GetDomAttribute(attributename).ToString.ToUpper();
                        break;
                    case "DomRadioButton":
                        DynamicObjectWait();
                        Actualattributevalue = objValue.DomRadioButton(fieldName).GetDomAttribute(attributename).ToString.ToUpper();
                        break;
                    case "DomLabel":
                        DynamicObjectWait();
                        Actualattributevalue = objValue.DomRadioButton(fieldName).GetDomAttribute(attributename).ToString.ToUpper();
                        break;
                }
                WriteConsoleLog(("Actual data " + fieldData));
                WriteConsoleLog(("Expected data " + inputdata));
                if ((Expectedattributevalue.Equals(Actualattributevalue) != true)) {
                    resultFlag = "Fail";
                    WriteConsoleLog(("******************* " 
                                    + (scenarioname + " failed *******************")));
                }
                else {
                    resultFlag = "Pass";
                }
                
                actualMessage = fieldData;
                stepExpectedResult = inputdata;
            }
            catch (Exception ex) {
                WriteConsoleLog(("VerifyFieldProperty exception generated. " + ex.Message));
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
        WriteConsoleLog("VerifyFieldProperty finished");
    }
    
    public final void LaunchSocketClient() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                Process ps;
                for (ps : Process.GetProcesses) {
                    if ((ps.ProcessName == "SocketClient")) {
                        ps.Kill;
                    }
                    
                    //                 If ps.ProcessName = "SocketClient" Then
                    //                     ps.Kill
                    //                 End If
                }
                
                Process.Start(inputdata);
                Agent.Attach("SocketClient.exe", "");
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void SocketClientInput() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                // With...
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    // Public Sub SocketClientMessage()    
    //         
    //         If resultFlag = "Pass" Or resultFlag = "Recover" Then
    //             Try                                
    // 
    //             inputdataSplit=Split(inputdata,";")
    //                 Dim IRNumber=inputdataSplit(0)
    //             
    //                 
    //                 'msgbox(ACCNUM)
    //                 
    //               Select Case (IRNumber)
    //                     
    //                 Case "IR-19070055_P"
    //                     
    //                     Dim CIFNO=inputdataSplit(1)
    //                     Dim Date1 =inputdataSplit(2)
    //                     Dim Date2 =inputdataSplit(3)
    //                     Dim Collateral =inputdataSplit(4)
    //                     Dim teller=inputdataSplit(1)
    //                     
    //                     Dim Messagetoserver = " 1030                    **  0000      00300437013000"+teller+"067157000000000     0    Q    00000000        000000000000000000000000000000000000"+Collateral+"Test                                              000000"++"Mr. Test  Prepod                                            03020043700000000003375000+00000000004500000+00000000003375000+00000000000000000+00000000000000000+100000000000000000+INR01052021180720210000000000005Red Test                                                                                                                                              0000000000000000010000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+N0000000000000000  0007003010520210000000000000000007004''?@ @C $3UY00000000000000000     00000000"
    //                     End With 
    //                     
    //                 Case "IR-19070055_N"
    //                     
    //                     Dim CIFNO=inputdataSplit(1)
    //                     Dim Date1 =inputdataSplit(2)
    //                     Dim Date2 =inputdataSplit(3)
    //                     Dim Collateral =inputdataSplit(4)
    //                     Dim teller=inputdataSplit(1)
    //                     
    //                     Dim Messagetoserver = " 1030                    **  0000      00300437013000"+teller+"067157000000000     0    Q    00000000        000000000000000000000000000000000000"+Collateral+"Test                                              000000"++"Mr. Test  Prepod                                            03020043700000000003375000+00000000004500000+00000000003375000+00000000000000000+00000000000000000+100000000000000000+INR01052021180720210000000000005Red Test                                                                                                                                              0000000000000000010000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+N0000000000000000  0007003010520210000000000000000007004''?@ @C $3UY00000000000000000     00000000"
    //                     End With 
    //                  
    //                 
    //                End Select
    //             
    //                 Catch ex As Exception
    //                     actualMessage = Err.Description    
    //                     Call SetRecoveryAndResultFlag()
    //                             
    //                         'recoveryFlag=1
    //                     Call WriteToOutputFile()
    //             End Try
    //         End If
    // End Sub    
    public final void SocketClientNavigate() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                // With...
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void SocketClientWebInput() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object teller;
                object CIF;
                object Date1;
                object Collateral;
                object Messagetoserver;
                object IRNumber;
                inputdataSplit = inputdata.Split(";");
                IRNumber = inputdataSplit(0);
                switch (IRNumber) {
                    case "IR-19070055_P":
                        teller = inputdataSplit(1);
                        CIF = inputdataSplit(2);
                        Date1 = inputdataSplit(3);
                        // Dim Date2 =inputdataSplit(3)
                        Collateral = inputdataSplit(4);
                        if ((Date1.IndexOf("Server") + 1)) {
                            object IRNumber1 = IRNumber.Split("_")[0];
                            retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, IRNumber1, Date1);
                        }
                        
                        Messagetoserver = (" 1030                    **  0000      00300437013000" 
                                    + (teller + ("067157000000000     0    Q    00000000        000000000000000000000000000000000000" 
                                    + (Collateral + ("Test                                              000000" 
                                    + (CIF + ("Mr. Test  Prepod                                            03020043700000000003375000+00000000004500" +
                                    "000+00000000003375000+00000000000000000+00000000000000000+100000000000000000+INR" 
                                    + (Date1 + @"180720210000000000005Red Test                                                                                                                                              0000000000000000010000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+0000000000000000000000  00000000000000000+00000000000000000+N0000000000000000  0007003010520210000000000000000007004''?@ @C $3UY00000000000000000     00000000"))))))));
                        objValue.DomTextField(fieldname).ClearText;
                        objValue.DomTextField(fieldname).SetText(Messagetoserver);
                        MsgBox(Messagetoserver);
                        break;
                    case "IR-19070055_N":
                        teller = inputdataSplit(1);
                        CIF = inputdataSplit(2);
                        Date1 = inputdataSplit(3);
                        if ((Date1.IndexOf("Server") + 1)) {
                            object IRNumber1 = IRNumber.Split("_")[0];
                            retrieved_Data = GetRetirevData(lookupFilePath, lookupFileName, IRNumber1, Date1);
                        }
                        
                        // Messagetoserver = "    **  0000      00300437035000"+teller+"062040000000000     0    Q    00000000        000000000000000000000000000000000000"+CIF+"                              Mr. Test  Prepod                                            0302Test                                              00000000005000000+00000000000000000+00000000000000000+1INR"+Date1+"310720210000000000005"
                        Messagetoserver = (" 0713                    **  0000      00300437236000" 
                                    + (teller + ("062040000000000     0    Q    00000000        000000000000000000000000000000000000" 
                                    + (CIF + ("                              Mr. Test  Prepod                                            0303Test   " +
                                    "                                           00000000005000000+00000000000000000+00000000000000000+1IN" +
                                    "R" 
                                    + (Date1 + @"010420210000000000005                                                                                                                                                      N0000000000000000  0000000000000000010000PY0000000000000000000000  0000000000000000000000  0000000000000000000000  0000000000000000000000  0000000000000000000000  C 0000000000000000000000"))))));
                        objValue.DomTextField(fieldname).ClearText;
                        objValue.DomTextField(fieldname).SetText(Messagetoserver);
                        MsgBox(Messagetoserver);
                        break;
                }
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void SocketClientMessageToServer() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                inputdataSplit = inputdata.Split(";");
                object IRNumber = inputdataSplit(0);
                object ACCNUMV = inputdataSplit(1);
                // msgbox(ACCNUM)
                switch (IRNumber) {
                    case "IR-18090089-001":
                        object ACCNUM = GetRetirevData(lookupFilePath, lookupFileName, IRNumber, ACCNUMV);
                        object Messagetoserver = ("0155                    **  0000      003043710000000017060495000000000     0         00000000       " +
                        " 000000000000000000000003505169003000000" 
                                    + (ACCNUM + "DEP0002"));
                        // With...
                        break;
                    case "IR-18090089-001-DOC":
                        object ACCNUM = GetRetirevData(lookupFilePath, lookupFileName, IRNumber, ACCNUMV);
                        object Messagetoserver = ("0155                    **  0000      003043710000000017060495000000000     0         00000000       " +
                        " 000000000000000000000003505169003000000" 
                                    + (ACCNUM + "DEP0002"));
                        // With...
                        break;
                    case "IR-18090089-001-Loan":
                        object Messagetoserver = ("0785                    **  0000      003004370730007003012000000000000     " 
                                    + (ACCNUMV + @"         00000000        0000000000000000000000000000000000000000000000000000085001446496PORT789       04Mr. AMIT  MISHRA                                            ASDAS                                                                           ASDAS                                   IN Jalgaon                               NN400614                           62111001000000000100000000+00000000002400000000000000000+00000000000000000000000000000+1000000000000000000 0000M  0100000000000000000000000000000000000010000000000000000000+000000000000+98 00000000000000000+0 ZZZZZZZ 00 000000000000000000 0000000+000000000000000   00000000000000000000000000 000 00000000000+00N0706    00000000I             00RMPB1"));
                        // With...
                        break;
                    case "IR-18090089-001-CCOD":
                        object Messagetoserver = ("1099                    **  0000      003004370460007003002013000000000     0     " 
                                    + (ACCNUMV + @"   10000000        0007004000000000000000000000000000000000000000000000085001446496STEVE899      03Mr. STEVE  SMITH                                            ZAIN S ASDASD           gaon                                                    N                                        IN                                         400614  60591001   R   00000000000000000000001  01000000000000000000000000NN    0000000000000000       000000000000000000  0000000000           000000000000000                 0000 00000000000000000            0 ZZZZZZZ                                     00000000000 0000000+0     00000000000000000                   0000000000000706         NNRMPB5           0000000000000000000000N00000000     00000000000000000+000N L0000000000000000                                                                                                                                                     000000000000000000000 00000                                                      0000000000000000000+0000000  I"));
                        // With...
                        break;
                    case "IR-19010029-LON":
                        object Messagetoserver = " 0178172102011045ISNDHRc00134END000000300020305420000003000194821300000000001000000+INRLOAN REPAYMENT" +
                        " CUSTOM NARRATION                                                                 Y";
                        // With...
                        break;
                    case "IR-19030128":
                        msgbox(ACCNUMV);
                        object inputdataSplit = ACCNUMV.Split("-");
                        object ACCNUMV1 = inputdataSplit[0];
                        object TellerId = inputdataSplit[1];
                        object IT = inputdataSplit[2];
                        object Messagetoserver = (" 0233                    **  0000      00300437020000" 
                                    + (TellerId + ("051300000000000     0         10000000        000700300000000000000000000000000000" 
                                    + (ACCNUMV1 + ("      " 
                                    + (IT + "0100100000001200000001000000000+       Z            22  27545555"))))));
                        // With...
                        break;
                    case "IR-20060078_N":
                        object IRNumber1 = IRNumber.Split("_")[0];
                        object ACCNUM = GetRetirevData(lookupFilePath, lookupFileName, IRNumber1, ACCNUMV);
                        object Messagetoserver = (" 1083                    **  0000      003004372080001117067153000000000     0     Q   10000000      " +
                        "  000010600000000000000000000000000000" 
                                    + (ACCNUM + @"LON0000000000000000       000000000000000000000000000000000+    0000000000000000000000000000000000000000           N  00000000000000000+00000000000000000+  00000000  00000000000000000+00000000  00000000                 0000000000000000000000000+000000000000000000000000000000000+00000000     00000000000000000+00000000000000000+00000000000000000+00000000000000000+  00000000000000000+00000000  00000000000000000+  00000000000000000+0000000000000000000000000+00000000000000000+00000000000000000+00000000000000000+00000000000000000+00000000000000000+00000000000000000000000000000000000000000+00000000000000000+  00000000000000000+0000000000000000000000000+00000000000000000+AMEND                                                                                   00000000000000000+   0 N0000000000000000+        00000000000000000+00000000000000000+00000000000000000+               0000000000000000    00000000     0000000000000000000 N 0000"));
                        // With...
                        break;
                    case "IR-20060078_P":
                        object IRNumber1 = IRNumber.Split("_")[0];
                        object ACCNUM = GetRetirevData(lookupFilePath, lookupFileName, IRNumber1, ACCNUMV);
                        object Messagetoserver = (" 1083                    **  0000      003004372080001117067153000000000     0     Q   10000000      " +
                        "  000010600000000000000000000000000000" 
                                    + (ACCNUM + @"LON0000000000000000       000000000000000000000000000000000+    0000000000000000000000000000000000000000           N  00000000000000000+00000000000000000+  00000000  00000000000000000+00000000  00000000                 0000000000000000000000000+000000000000000000000000000000000+00000000     00000000000000000+00000000000000000+00000000000000000+00000000000000000+  00000000000000000+00000000  00000000000000000+  00000000000000000+0000000000000000000000000+00000000000000000+00000000000000000+00000000000000000+00000000000000000+00000000000000000+00000000000000000000000000000000000000000+00000000000000000+  00000000000000000+0000000000000000000000000+00000000000000000+AMEND                                                                                   00000000000000000+   0 Y0000000000000000+        00000000000000000+00000000000000000+00000000000000000+               0000000000000000    00000000     0000000000000000000 N 0000"));
                        // With...
                        break;
                }
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void SocketClientMessageFromServer() {
        if (((resultFlag == "Pass") 
                    || (resultFlag == "Recover"))) {
            try {
                object ExpMsg;
                // With...
                ServerMsg = silktest.FormsWindow("Socket Client").TextField(fieldName).Text;
                // ServerMsg="01351721021000002415201/02/2021O.K.       AUTOMATION PAYAL              MEHTA                              00000025493778000                                                                                    Y"
                // ServerMsg ="0960    0830            00000000000123003047910040000019060495000002335000044130200   00000000        0000000000000000000000035051690300300000030002035813DEP000200000081145652302CUS RAMU KUMAR D                                               00000081145652313CUS SK  KALIMUDDIN                                             00000081145652324CUS ASIF  IQUBAL                                               00000091145652335CUS SANJAYA  MOHAPATRA                                         00000081145657140CUS RABINDRA  MOHAPATRA                                        00000081145657151CUS ABAKASH  DAS                                               00000081145657162CUS CHAKRADHAR  GIRI                                           00000085001419188CUSTHINKSOFT                                                   00000085001803073CUS AjinkyaAjinkya CUST-M-NAME-01092020010001 ChavanChavan     00000085001816980CUSMr. Aryan  Kio "
                switch (inputdata) {
                    case "IR-18090089-001":
                        object temp = "CUS";
                        int Occurance = ((ServerMsg.Length - ServerMsg.Replace(temp, String.Empty).Length) 
                                    / temp.Length);
                        for (i = 0; (i <= Occurance); i++) {
                            object CUSValue = ServerMsg.split("CUS")[i];
                            object custid = CUSValue.Substring((CUSValue.length - 9), CUSValue.length);
                            if ((custid.Substring(0, 1) == 8)) {
                                resultFlag = "Pass";
                            }
                            else {
                                resultFlag = "Fail";
                                actualMessage = "Invalid Customer ID";
                                break;
                            }
                            
                        }
                        
                        break;
                    case "IR-19010029":
                        object temprefno = ServerMsg.split("/")[0];
                        object refno = temprefno.Substring((temprefno.length - 6), temprefno.length);
                        object temp = refno.Substring(1, 5);
                        inputData = "REF NO";
                        fieldData = temp;
                        WriteToLookupFile();
                        break;
                    case "IR-18090089-001-N":
                        if (ServerMsg.Contains("6869")) {
                            object SCtemp = ServerMsg.split("6869")[1];
                            ExpMsg = "Account opening not allowed for Said Customer Type";
                            if ((ExpMsg == SCtemp)) {
                                resultFlag = "Pass";
                            }
                            else {
                                resultFlag = "Fail";
                                actualMessage = "Mismatch in Error Message";
                            }
                            
                        }
                        else {
                            resultFlag = "Fail";
                            actualMessage = "Invalid Message";
                        }
                        
                        break;
                    case "IR-18090089-001-Y":
                        if (ExpMsg.Contains(3000)) {
                            resultFlag = "Pass";
                        }
                        else {
                            resultFlag = "Fail";
                            actualMessage = "Account Number is not generated.";
                        }
                        
                        break;
                    case "IR-19030128-N":
                        ServerMsg = "hgfhgfhff 7825 Non-multicity cheque books can not be issued";
                        if (ServerMsg.Contains("7825 Non-multicity cheque books can not be issued")) {
                            resultFlag = "Pass";
                        }
                        else {
                            resultFlag = "Fail";
                            actualMessage = "Invalid Message";
                        }
                        
                        break;
                    case "IR-19030128-P":
                        ServerMsg = "hgfhgfhff 7825 Non-multicity cheque books can not be issued";
                        if (ServerMsg.Contains("O.K")) {
                            resultFlag = "Pass";
                        }
                        else {
                            resultFlag = "Fail";
                            actualMessage = "Invalid Message";
                        }
                        
                        break;
                    case "IR-20060078_N":
                        if (ServerMsg.Contains("8983 00 INCORRECT VALUE SELECTED IN AB-INITIO UNSECURED FLAG")) {
                            resultFlag = "Pass";
                        }
                        else {
                            resultFlag = "Fail";
                            actualMessage = "Invalid Message";
                        }
                        
                        break;
                    case "IR-20060078_P":
                        if (ServerMsg.Contains("O.K.")) {
                            resultFlag = "Pass";
                        }
                        else {
                            resultFlag = "Fail";
                            actualMessage = "Invalid Message";
                        }
                        
                        break;
                }
            }
            catch (Exception ex) {
                actualMessage = Err.Description;
                SetRecoveryAndResultFlag();
                // recoveryFlag=1
                WriteToOutputFile();
            }
            
        }
        
    }
    
    public final void ExtractAndLogProperties() {
        string ConsoleProp;
        try {
            WriteConsoleLog(("Attempting to recover properties of the object " + fieldName));
            List<string> propertyList;
            propertyList = objValue.TestObject(fieldName).GetPropertyList;
            ConsoleProp = "";
            object str_prop;
            object str_propVal;
            for (string TestProperty : propertyList) {
                str_prop = TestProperty;
                str_propVal = objValue.TestObject(fieldName).GetProperty(str_prop);
                ConsoleProp = (ConsoleProp + ("[@" 
                            + (str_prop.Replace(".", "_") + ("=" 
                            + (str_propVal.Replace(".", "_") + "]")))));
            }
            
            WriteConsoleLog(ConsoleProp);
        }
        catch (Exception ex) {
            WriteConsoleLog(("Exception generated in recovering properties of the object " 
                            + (fieldName + (". " + ex.Message))));
            WriteConsoleLog(ConsoleProp);
        }
        
    }
}