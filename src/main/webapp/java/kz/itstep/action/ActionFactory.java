package kz.itstep.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final Map<String, Action> PAGES = new HashMap<>();

    public ActionFactory() {
        init();
    }

    private void init(){
        PAGES.put("/info", new InfoAction());
        PAGES.put("/hi", new HiAction());
    }

    public Action getAction(HttpServletRequest request, HttpServletResponse response){
        return PAGES.get(request.getPathInfo());
    }
}
