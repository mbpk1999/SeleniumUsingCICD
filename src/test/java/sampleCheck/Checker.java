package sampleCheck;

import java.util.ArrayList;
import java.util.List;

public class Checker extends ExcelMethods{
    public static void main(String[] args)
    {
        ExcelMethods em = new ExcelMethods();
        //em.readDataFromExcel("Banking_Data.xlsx", "CustomerAccounts")
        //String[][] data = em.readFilteredColumnData("Banking_Data.xlsx", "CustomerAccounts", "CustomerName", "CurrentBalance");
        List<String> headerList = new ArrayList<>();
        headerList.add("Username");
        headerList.add("Passcode");
        headerList.add("DOB");

        em.writeDataToExcel("Banking_Data.xlsx", "Tester",headerList, "mbpk", "qwert", "12/09/1999");
    }
}
