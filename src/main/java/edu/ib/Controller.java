package edu.ib;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.mariuszgromada.math.mxparser.Function;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text wrongFunctionText;

    @FXML
    private LineChart<?, ?> graph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField functionTextField;

    @FXML
    private Button btnPlotFunction;

    @FXML
    private TextField domainRightText;

    @FXML
    private TextField domainLeftText;

    @FXML
    private ChoiceBox<String> methodChoiceBox;

    @FXML
    private Button calculateButton;

    @FXML
    private Text outputRootText;

    @FXML
    private TextField errorText;

    @FXML
    private Text functionValueInXText;

    @FXML
    private TextField x0;

    @FXML
    private TextField xLeft;

    @FXML
    private TextField xRight;

    @FXML
    private Text parametersErrorMessage;

    Function f = new Function("f(x)=");

    private int domainLeft = 0;

    private int domainRight = 10;

    private int sample = 1;

    ObservableList<String> methods = FXCollections.observableArrayList();

    private void setMethodChoiceButton() {
        String a = "Bisection";
        String b = "False position";
        String c = "Fixed-point";
        String d = "Newton-Raphson";
        String e = "Secant";
        methods.addAll(a, b, c, d, e);
        methodChoiceBox.getItems().addAll(methods);
        methodChoiceBox.setValue("Bisection");
    }

    @FXML
    private void accepted(ActionEvent event){
        graph.requestFocus();
    }

    @FXML
    void calculate(ActionEvent event) throws IllegalArgumentException {
        if (!f.getFunctionExpressionString().equals(functionTextField.getText())) plotFunction(event);
        String output = "x=";
        CalculateScalarFunction c = new CalculateScalarFunction();
        try {
            double xp = 0;
            double xl = 0;
            double xr = 0;
            if (methodChoiceBox.getValue().equals("Fixed-point") || methodChoiceBox.getValue().equals("Newton-Raphson"))
                xp = Double.parseDouble(x0.getText());
            else {
                xl = Double.parseDouble(xLeft.getText());
                xr = Double.parseDouble(xRight.getText());
            }
            double error = Double.parseDouble(errorText.getText());
            switch (methodChoiceBox.getValue()) {
                case "Bisection" -> output += c.bisection(xl, xr, error, (x) -> (f.calculate(x)));
                case "False position" -> output += c.falsi(xl, xr, error, (x) -> (f.calculate(x)));
                case "Fixed-point" -> output += c.fixedPoint(xp, error, (x) -> (f.calculate(x)));
                case "Newton-Raphson" -> output += c.tangent(xp, error, (x) -> (f.calculate(x)));
                case "Secant" -> output += c.secant(xl, xr, error, (x) -> (f.calculate(x)));
            }
            parametersErrorMessage.setVisible(false);
            if (output.contains("Failed")) {
                output = output.substring(2);
                outputRootText.setText(output);
                functionValueInXText.setText("f(x)=NaN");
            } else {
                outputRootText.setText(output);
                functionValueInXText.setText("f(x)=" + f.calculate(Double.parseDouble(output.substring(2))));
            }

        } catch (Exception e) {
            parametersErrorMessage.setVisible(true);
        }

    }

    @FXML
    void changeDomain(ActionEvent event) {
        if (domainRightText.equals(event.getSource())) {
            try {
                domainRight = Integer.parseInt(domainRightText.getText());
                sample = (domainRight - domainLeft) / 10;
                if (sample == 0) sample = 1;
                domainRightText.setText(String.valueOf(domainRight));
                if(!f.getFunctionExpressionString().isEmpty()) plotFunction(event);
                accepted(event);
                xAxis.setUpperBound(domainRight);
            } catch (Exception e) {
                e.getStackTrace();
                domainRightText.setText(String.valueOf(domainRight));
            }
        } else if (domainLeftText.equals(event.getSource())) {
            try {
                domainLeft = Integer.parseInt(domainLeftText.getText());
                sample = (domainRight - domainLeft) / 10;
                if (sample == 0) sample = 1;
                if(!f.getFunctionExpressionString().isEmpty()) plotFunction(event);
                accepted(event);
                xAxis.setLowerBound(domainLeft);
            } catch (Exception e) {
                e.getStackTrace();
                domainLeftText.setText(String.valueOf(domainLeft));
            }
        }

    }

    @FXML
    void plotFunction(ActionEvent event) {
        f = new Function("f(x)=" + functionTextField.getText());
        if (f.checkSyntax()) {
            wrongFunctionText.setVisible(false);
            XYChart.Series points = new XYChart.Series();
            for (int i = 0; i <= (domainRight-domainLeft) / (sample * 0.1); i++) {
                double x = domainLeft + i * sample * 0.1;
                if (!Double.isNaN(f.calculate(x)) && f.calculate(x) != Double.POSITIVE_INFINITY && f.calculate(x) != Double.NEGATIVE_INFINITY) {
                    points.getData().add(new XYChart.Data(x, f.calculate(x)));

                }
            }
            System.out.println((domainRight-domainLeft)/50.0);
            xAxis.setTickUnit((domainRight-domainLeft)/50.0);
            graph.getData().clear();
            graph.getData().add(points);
            System.out.println(xAxis.getTickUnit());
        } else {
            wrongFunctionText.setVisible(true);
            functionTextField.requestFocus();
        }
    }

    @FXML
    void initialize() {
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'graph.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'graph.fxml'.";
        assert yAxis != null : "fx:id=\"yAxis\" was not injected: check your FXML file 'graph.fxml'.";
        assert functionTextField != null : "fx:id=\"functionTextField\" was not injected: check your FXML file 'graph.fxml'.";
        assert wrongFunctionText != null : "fx:id=\"wrongFunctionText\" was not injected: check your FXML file 'graph.fxml'.";
        assert btnPlotFunction != null : "fx:id=\"btnPlotFunction\" was not injected: check your FXML file 'graph.fxml'.";
        assert methodChoiceBox != null : "fx:id=\"methodChoice\" was not injected: check your FXML file 'graph.fxml'.";
        assert calculateButton != null : "fx:id=\"calculateButton\" was not injected: check your FXML file 'graph.fxml'.";
        assert outputRootText != null : "fx:id=\"outputText\" was not injected: check your FXML file 'graph.fxml'.";
        assert functionValueInXText != null : "fx:id=\"functionValueInXText\" was not injected: check your FXML file 'graph.fxml'.";
        assert x0 != null : "fx:id=\"x0\" was not injected: check your FXML file 'graph.fxml'.";
        assert xLeft != null : "fx:id=\"xLeft\" was not injected: check your FXML file 'graph.fxml'.";
        assert xRight != null : "fx:id=\"xRight\" was not injected: check your FXML file 'graph.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'graph.fxml'.";
        assert domainLeftText != null : "fx:id=\"lowerXValue\" was not injected: check your FXML file 'graph.fxml'.";
        assert domainRightText != null : "fx:id=\"higherXValue\" was not injected: check your FXML file 'graph.fxml'.";
        assert parametersErrorMessage != null : "fx:id=\"parametersErrorMessage\" was not injected: check your FXML file 'graph.fxml'.";
        setMethodChoiceButton();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(domainLeft);
        xAxis.setUpperBound(domainRight);
        x0.setDisable(true);

        methodChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            switch ((Integer) newValue) {
                case 2, 3 -> {
                    if(x0.disabledProperty().getValue())
                        x0.setText(String.valueOf(Math.floor((Double.parseDouble(domainRightText.getText())+Double.parseDouble(domainLeftText.getText()))/2.0)));
                    x0.setDisable(false);
                    xLeft.clear();
                    xLeft.setDisable(true);
                    xRight.clear();
                    xRight.setDisable(true);

                }
                default -> {
                    x0.clear();
                    x0.setDisable(true);
                    if(xLeft.disabledProperty().getValue())
                        xLeft.setText(String.valueOf(domainLeft));
                    if(xRight.disabledProperty().getValue())
                    xRight.setText(String.valueOf(domainRight));
                    xLeft.setDisable(false);
                    xRight.setDisable(false);


                }
            }
        });

        x0.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty() && !newValue.equals("-")) Double.parseDouble(newValue);
            } catch (Exception e) {
                e.getStackTrace();
                x0.setText(oldValue);
            }
        });

        xLeft.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty() && !newValue.equals("-")) Double.parseDouble(newValue);
            } catch (Exception e) {
                e.getStackTrace();
                xLeft.setText(oldValue);
            }
        });

        xRight.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty() && !newValue.equals("-")) Double.parseDouble(newValue);
            } catch (Exception e) {
                e.getStackTrace();
                xRight.setText(oldValue);
            }
        });

        errorText.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) Double.parseDouble(newValue);
            } catch (Exception e) {
                e.getStackTrace();
                errorText.setText(oldValue);
            }
        });
    }
}
