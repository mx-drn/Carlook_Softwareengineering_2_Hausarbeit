package org.control;

import com.vaadin.ui.Notification;
import org.control.exception.DataBaseException;
import org.model.dao.AutoDao;
import org.model.dao.DaoFactory;
import org.model.entity.Auto;

public class AngebotErstellenControl {

    public static void angebotErstellen(Auto autoDTO) {
        try {
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();
            autoDao.createAuto(autoDTO);

            Notification.show("Angebot erfolgreich erstellt.", Notification.Type.HUMANIZED_MESSAGE);

        } catch (DataBaseException e) {
            Notification.show(e.getReason(), Notification.Type.ERROR_MESSAGE);
        }


    }

}
