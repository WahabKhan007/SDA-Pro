package dashboard.views;
import dashboard.models.DashboardModel;
public class DashboardView {
    public void render(DashboardModel model) {
        System.out.println("========== SOC DASHBOARD (MVC View) ==========");
        for (String message : model.getMessages()) System.out.println(message);
        System.out.println("=============================================");
    }
}
