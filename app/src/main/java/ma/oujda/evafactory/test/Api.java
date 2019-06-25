package ma.oujda.evafactory.test;

public class Api {

    private static final String ROOT_URL = "http://192.168.1.108/Account/v1/Api.php?apicall=";

    public static final String URL_CREATE_User = ROOT_URL + "createUser";
    public static final String URL_READ_User = ROOT_URL + "getUser&Email=";
    public static final String URL_UPDATE_User = ROOT_URL + "updateUser";
    public static final String URL_DELETE_User = ROOT_URL + "deleteUser&id=";

}
