package crud;

import base.BaseTest;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
import org.testng.annotations.Test;

public class TestCreateBooking extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive() {

        System.out.println("I am in the starting of the test case!");
    }


    @Test(groups = "reg", priority = 1)
    @Owner("Abhishek")
    @Description("TC#1 - Verify that the Booking can't be Created, When Payload is null")
    public void testCreateBookingPOST_Negative() {

        System.out.println("I am in the starting of the test case!");
    }







    }
