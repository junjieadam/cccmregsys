package biz.cccm.mgmt.registration.action;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.joda.time.DateTime;
import biz.cccm.mgmt.registration.dao.exceptions.NonexistentEntityException;
import biz.cccm.mgmt.registration.model.Mealplan;
import biz.cccm.mgmt.registration.service.MealplanManager;
import biz.cccm.webapp.action.BasePage;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author chehhoo
 */
public class MealplanForm extends BasePage implements Serializable {

    private static final long serialVersionUID = -1141119853856863205L;
    private MealplanManager mealplanManager;
    private String id;
    private Mealplan mealplan = new Mealplan();

    //Generated by Map
    private static final Map<String, Integer> quantities;

    static {
        quantities = new LinkedHashMap<String, Integer>();
        quantities.put("0", 0); 
        quantities.put("1", 1); //label, value
        quantities.put("2", 2);
        quantities.put("3", 3);
        quantities.put("4", 4); //label, value
        quantities.put("5", 5);
        quantities.put("6", 6);
        quantities.put("7", 7); //label, value
        quantities.put("8", 8);
        quantities.put("9", 9);
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMealplanManager(MealplanManager mealplanManager) {
        this.mealplanManager = mealplanManager;
    }

    public Mealplan getMealplan() {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
//        for (Map.Entry pairs : params.entrySet()) {
//            log.debug(pairs.getKey() + " = " + pairs.getValue());
//        }
        log.debug("getting mealplan, id is: " + id + " | " + mealplan.getRegistrationID());
//        if (mealplan == null) {
//            mealplan = mealplanManager.getMealplan(id);
//        }
        return mealplan;
    }

    public void setMealplan(Mealplan mealplan) {
        this.mealplan = mealplan;
    }

    public String add() {
        mealplan = new Mealplan();
        return "editMealplan";
    }

    public String edit() {
        log.debug("getting mealplan, id is: " + id);
        mealplan = mealplanManager.getMealplan(id);
        return "editMealplan";
    }

    public String save() throws IOException {

        log.debug("getting mealplan, id is: " + id + " | " + mealplan.getRegistrationID());

        try {
            mealplanManager.saveMealplan(mealplan);
            // add success messages
            addMessage("mealplan.saved");
        } catch (NonexistentEntityException e) {
            addError("errors.update.mealplan");
            log.error("NonexistentEntityException: " + e.getMessage());
        }

        log.debug("Exit after saved...");

        return "list";
    }

    public String delete() {

        mealplanManager.removeMealplan(mealplan.getRegistrationID());
        addMessage("mealplan.deleted", getMealplan().getRegistrationID());

        return "list";
    }

    public String cancel() {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'cancel' method");
        }

        if (!"list".equals(getParameter("from"))) {
            return "home";
        } else {
            return "list";
        }
    }

    public Map getQuantities() {
        return quantities;
    }
}
