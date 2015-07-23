package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "androidcourse.android.jonaswu.yahoo.com.todolists.dao_helper");
        Entity box = schema.addEntity("todolist");
        box.addIdProperty();
        box.addStringProperty("name");
        box.addDateProperty("due");
        box.addDateProperty("create");
        new DaoGenerator().generateAll(schema, args[0]);
    }
}
