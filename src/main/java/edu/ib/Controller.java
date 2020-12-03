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
    private TextField higherXValue;

    @FXML
    private TextField lowerXValue;

    @FXML
    private ChoiceBox<String> methodChoice;

    @FXML
    private Button calculateButton;

    @FXML
    private Text outputText;

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

    //zmienne programu
    Function f = new Function("f(x)=0");
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
        methodChoice.getItems().addAll(methods);
        methodChoice.setValue("Bisection");
    }

    @FXML
    void calculate(ActionEvent event) throws IllegalArgumentException {
        if (!f.getFunctionExpressionString().equals("f(x)=" + functionTextField.getText())) plotFunction(event);
        String output = "x=";
        CalculateScalarFunction c = new CalculateScalarFunction();
        try {
            double xp = 0;
            double xl = 0;
            double xr = 0;
            if (methodChoice.getValue().equals("Fixed-point") || methodChoice.getValue().equals("Newton-Raphson"))
                xp = Integer.parseInt(x0.getText());
            else {
                xl = Integer.parseInt(xLeft.getText());
                xr = Integer.parseInt(xRight.getText());
            }
            double error = Double.parseDouble(errorText.getText());
            switch (methodChoice.getValue()) {
                case "Bisection" -> output += c.bisection(xl, xr, error, (x) -> (f.calculate(x)));
                case "False position" -> output += c.falsi(xl, xr, error, (x) -> (f.calculate(x)));
                case "Fixed-point" -> output += c.fixedPoint(xp, error, (x) -> (f.calculate(x)));
                case "Newton-Raphson" -> output += c.tangent(xp, error, (x) -> (f.calculate(x)));
                case "Secant" -> output += c.secant(xl, xr, error, (x) -> (f.calculate(x)));
                default -> throw new IllegalArgumentException("Wrong input argument: " + methodChoice.getValue());
            }
            if (output.contains("Failed")) {
                output = output.substring(2);
                outputText.setText(output);
                functionValueInXText.setText("f(x)=NaN");
            } else {
                outputText.setText(output);
                functionValueInXText.setText("f(x)=" + f.calculate(Double.parseDouble(output.substring(2))));
            }

        } catch (Exception e) {

        }

    }

    @FXML
    void changeDomain(ActionEvent event) {
        if (higherXValue.equals(event.getSource())) {
            try {
                domainRight = Integer.parseInt(higherXValue.getText());
                sample = (domainRight - domainLeft) / 10;
                if (sample == 0) sample = 1;
                higherXValue.setText(String.valueOf(domainRight));
                plotFunction(event);
                graph.requestFocus();
                xAxis.setUpperBound(domainRight);
            } catch (Exception e) {
                e.getStackTrace();
                higherXValue.setText(String.valueOf(domainRight));
            }
        } else if (lowerXValue.equals(event.getSource())) {
            try {
                domainLeft = Integer.parseInt(lowerXValue.getText());
                sample = (domainRight - domainLeft) / 10;
                if (sample == 0) sample = 1;
                plotFunction(event);
                graph.requestFocus();
                xAxis.setLowerBound(domainLeft);
            } catch (Exception e) {
                e.getStackTrace();
                lowerXValue.setText(String.valueOf(domainLeft));
            }
        }

    }

    @FXML
    void plotFunction(ActionEvent event) {
        f = new Function("f(x)=" + functionTextField.getText());
        if (f.checkSyntax()) {
            wrongFunctionText.setVisible(false);
            XYChart.Series points = new XYChart.Series();
            for (int i = domainLeft; i <= domainRight / (sample * 0.1); i++) {
                double x = domainLeft + i * sample * 0.1;
                if (!Double.isNaN(f.calculate(x)) && f.calculate(x) != Double.POSITIVE_INFINITY && f.calculate(x) != Double.NEGATIVE_INFINITY) {
                    points.getData().add(new XYChart.Data(x, f.calculate(x)));
                }
            }
            graph.getData().clear();
            graph.getData().add(points);
        } else {
            wrongFunctionText.setVisible(true);
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
        assert methodChoice != null : "fx:id=\"methodChoice\" was not injected: check your FXML file 'graph.fxml'.";
        assert calculateButton != null : "fx:id=\"calculateButton\" was not injected: check your FXML file 'graph.fxml'.";
        assert outputText != null : "fx:id=\"outputText\" was not injected: check your FXML file 'graph.fxml'.";
        assert functionValueInXText != null : "fx:id=\"functionValueInXText\" was not injected: check your FXML file 'graph.fxml'.";
        assert x0 != null : "fx:id=\"x0\" was not injected: check your FXML file 'graph.fxml'.";
        assert xLeft != null : "fx:id=\"xLeft\" was not injected: check your FXML file 'graph.fxml'.";
        assert xRight != null : "fx:id=\"xRight\" was not injected: check your FXML file 'graph.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'graph.fxml'.";
        assert lowerXValue != null : "fx:id=\"lowerXValue\" was not injected: check your FXML file 'graph.fxml'.";
        assert higherXValue != null : "fx:id=\"higherXValue\" was not injected: check your FXML file 'graph.fxml'.";
        setMethodChoiceButton();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(domainLeft);
        xAxis.setUpperBound(domainRight);
        x0.setDisable(true);
        methodChoice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            switch ((Integer) newValue) {
                case 2, 3 -> {
                    x0.setDisable(false);
                    xLeft.clear();
                    xLeft.setDisable(true);
                    xRight.clear();
                    xRight.setDisable(true);
                }
                default -> {
                    x0.clear();
                    x0.setDisable(true);
                    xLeft.setDisable(false);
                    xRight.setDisable(false);
                }
            }
        });
        x0.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) Double.parseDouble(newValue);
            } catch (Exception e) {
                e.getStackTrace();
                x0.setText(oldValue);
            }
        });
        xLeft.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) Double.parseDouble(newValue);
            } catch (Exception e) {
                e.getStackTrace();
                xLeft.setText(oldValue);
            }
        });
        xRight.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) Double.parseDouble(newValue);
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
