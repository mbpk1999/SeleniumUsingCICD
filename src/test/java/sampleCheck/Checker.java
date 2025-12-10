package sampleCheck;

public class Checker extends ExcelMethods{
    public static void main(String[] args)
    {
        ExcelMethods em = new ExcelMethods();
        //em.readDataFromExcel("Banking_Data.xlsx", "CustomerAccounts")
        String[][] data = em.readFilteredColumnData("Banking_Data.xlsx", "CustomerAccounts", "CustomerName", "CurrentBalance");
    }
}
