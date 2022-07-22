package ir.alirezaalijani.product.manager.application.ui.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class ViewPath {

    // alerts view files
    public static final URL INFO_ALERT = getUrl("view","alert","alertInfo.fxml");
    public static final URL INFO_ALERT_W = getUrl("view","alert","alertInfoW.fxml");
    public static final URL QU_ANSWER_ALERT = getUrl("view","alert","alertQuAnser.fxml");
    public static final URL QU_ALERT = getUrl("view","alert","alertQu.fxml");

    // application view files
    public static URL applicationMain = getUrl("ApplicationMain.fxml");
    public static URL ViewMain=getUrl("view","MainView.fxml");
    public static URL ViewProducts = getUrl("view","Products.fxml");
    public static URL ViewProduct = getUrl("view","Product.fxml");

    // list item files
    public static URL ViewOrderItemCell= getUrl("view","OrderListItem.fxml");

    // application icon
    public static String imgApplicationIcon= Objects.requireNonNull(getUrl("img", "icons8-product-64.png")).toString();



    private static URL getUrl(String... path) {
        try {
            return new URL(Paths.get(ResourcePath.path, path).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
