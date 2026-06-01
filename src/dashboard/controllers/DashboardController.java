package dashboard.controllers;
import dashboard.models.DashboardModel;
import dashboard.views.DashboardView;

public class DashboardController {
    private DashboardModel model;
    private DashboardView view;
    public DashboardController(DashboardModel model, DashboardView view) { this.model = model; this.view = view; }
    public void addDashboardMessage(String message) { model.addMessage(message); }
    public void showDashboard() { view.render(model); }
}
