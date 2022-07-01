module Tao.Liu_Javafx_SceneBuilderTest {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml,table_Name;
	opens image to javafx.graphics, javafx.fxml;
	opens util to javafx.graphics, javafx.fxml, javafx.base;
	opens model to javafx.base;
}
