package day03.giangth.onlinemanagement.ultil;

public class Server {
//    public static String localhost = "172.20.10.10";
public static String localhost = "172.20.10.10";
    // Network connection Ih
    public static String URLTypeDevice = "http://" + localhost + "/server/getTypeDevice.php";
    public static String URLBrandNewProduct = "http://" + localhost + "/server/getBrandNewProduct.php";
    public static String URLPhone = "http://" + localhost + "/server/getProduct.php?page=";
    public static String URLOrder = "http://" + localhost + "/server/purchaseinformation.php";
    public static String URLOrderInformation = "http://" + localhost + "/server/orderpurchaseinformation.php";
}

