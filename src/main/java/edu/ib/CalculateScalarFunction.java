package edu.ib;

public class CalculateScalarFunction {
    /**
     * Method counting x, for which f(x)=0 with error eps using bisection method
     *
     * @param xLeft    Start of range
     * @param xRight   End of range
     * @param eps      Error
     * @param function ScalarFunction to estimate
     * @return Value of x, for which f(x)=0
     */
    public String bisection(double xLeft, double xRight, double eps, ScalarFunction function) {
        if (function.f(xLeft) * function.f(xRight) < 0) {
            double blad = 0;
            double x = (xLeft + xRight) / 2;
            double oldxr;
            int counter = 1;

            do {
                if (function.f(xLeft) * function.f(x) < 0) {
                    xRight = x;
                } else if (function.f(x) * function.f(xRight) < 0) {
                    xLeft = x;
                } else {
                    //System.out.println("x=" + x + " EpsA=0");
                    break;
                }
                oldxr = x;
                x = (xRight + xLeft) / 2;
                blad = Math.abs((x - oldxr) / x);
                //System.out.println(counter++ + " xLeft=" + xLeft + " xRight=" + xRight + " x=" + x + " EpsA=" + blad);
            } while (eps <= blad);
            return Double.toString(x);
        } else if (function.f(xLeft) == 0) {
            //System.out.println("x=" + xLeft + "EpsA=0");
            return String.valueOf(xLeft);
        } else if (function.f(xRight) == 0) {
            //System.out.println("x=" + xRight + "EpsA=0");
            return String.valueOf(xRight);
        } else {
            //System.out.println("Value f(xLeft)*f(xRight)>0");
            return "Failed, try using other method or change input parameters.";
        }
    }

    /**
     * Method counting x, for which f(x)=0 with error eps and comparing to number
     *
     * @param xLeft      Start of range
     * @param xRight     End of range
     * @param eps        Error
     * @param function   ScalarFunction to estimate
     * @param xToCompare Value of x to compare to estimated value
     * @return Value of x, for which f(x)=0
     */
    public String bisection(double xLeft, double xRight, double eps, ScalarFunction function, double xToCompare) {
        if (function.f(xLeft) * function.f(xRight) < 0) {
            double blad = 0;
            double x = (xLeft + xRight) / 2;
            double oldxr;
            int counter = 1;

            do {
                if (function.f(xLeft) * function.f(x) < 0) {
                    xRight = x;
                } else if (function.f(x) * function.f(xRight) < 0) {
                    xLeft = x;
                } else {
                    //System.out.println("x=" + x + " EpsA=0" + " EpsT=" + Math.abs((x - xToCompare) / xToCompare));
                    break;
                }
                oldxr = x;
                x = (xRight + xLeft) / 2;
                blad = Math.abs((x - oldxr) / x);
                //System.out.println(counter++ + " xLeft=" + xLeft + " xRight=" + xRight + " x=" + x + " EpsA=" + blad + " EpsT=" + Math.abs((x - xToCompare) / xToCompare));
            } while (eps <= blad);
            return String.valueOf(x);
        } else if (function.f(xLeft) == 0) {
            //System.out.println("x=" + xLeft + "EpsA=0 EpsT=" + Math.abs((xLeft - xToCompare) / xToCompare));
            return String.valueOf(xLeft);
        } else if (function.f(xRight) == 0) {
            //System.out.println("x=" + xRight + "EpsA=0 EpsT=" + Math.abs((xLeft - xToCompare) / xToCompare));
            return String.valueOf(xRight);
        } else {
            //System.out.println("Value f(xLeft)*f(xRight)>0");
            return "Failed, try using other method or change input parameters.";
        }
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using falsi method
     *
     * @param xLeft    Start of range
     * @param xRight   End of range
     * @param eps      Error
     * @param function ScalarFunction to estimate
     * @return Value of x, for which f(x)=0
     */
    public String falsi(double xLeft, double xRight, double eps, ScalarFunction function) {
        if (function.f(xLeft) * function.f(xRight) < 0) {
            double blad = 0;
            double x = xLeft - (function.f(xLeft) * (xRight - xLeft) / (function.f(xRight) - function.f(xLeft)));
            int counter = 1;
            double oldxr = x;

            do {
                if (function.f(x) == 0) {
                    break;
                } else if (function.f(x) * function.f(xLeft) < 0) {
                    xRight = x;
                } else xLeft = x;

                x = xLeft - (function.f(xLeft) * (xRight - xLeft) / (function.f(xRight) - function.f(xLeft)));
                blad = Math.abs((x - oldxr) / x);
                //System.out.println(counter++ + " xLeft=" + xLeft + " xRight=" + xRight + " x=" + x + " EpsA=" + blad);
                oldxr = x;
            } while (eps <= blad);
            return String.valueOf(x);
        } else if (function.f(xLeft) == 0) {
            //System.out.println("x=" + xLeft + "EpsA=0");
            return String.valueOf(xLeft);
        } else if (function.f(xRight) == 0) {
            //System.out.println("x=" + xRight + "EpsA=0");
            return String.valueOf(xRight);
        } else {
            //System.out.println("Value f(xLeft)*f(xRight)>0");
            return "Failed, try using other method or change input parameters.";
        }
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using falsi method and comparing to number
     *
     * @param xLeft      Start of range
     * @param xRight     End of range
     * @param eps        Error
     * @param function   ScalarFunction to estimate
     * @param xToCompare Value of x to compare to estimated value
     * @return Value of x, for which f(x)=0
     */
    public String falsi(double xLeft, double xRight, double eps, ScalarFunction function, double xToCompare) {
        if (function.f(xLeft) * function.f(xRight) < 0) {
            double blad = 0;
            double x = xLeft - (function.f(xLeft) * (xRight - xLeft) / (function.f(xRight) - function.f(xLeft)));
            int counter = 1;
            double oldxr = x;

            do {
                if (function.f(x) == 0) {
                    break;
                } else if (function.f(x) * function.f(xLeft) < 0) {
                    xRight = x;
                } else xLeft = xRight;

                x = xLeft - (function.f(xLeft) * (xRight - xLeft) / (function.f(xRight) - function.f(xLeft)));
                blad = Math.abs((x - oldxr) / x);
                //System.out.println(counter++ + " xLeft=" + xLeft + " xRight=" + xRight + " x=" + x + " EpsA=" + blad + " EpsT=" + Math.abs((x - xToCompare) / xToCompare));
                oldxr = x;
            } while (eps <= blad);
            return String.valueOf(x);
        } else if (function.f(xLeft) == 0) {
            //System.out.println("x=" + xLeft + "EpsA=0" + " EpsT=" + Math.abs((xLeft - xToCompare) / xToCompare));
            return String.valueOf(xLeft);
        } else if (function.f(xRight) == 0) {
            //System.out.println("x=" + xRight + "EpsA=0" + " EpsT=" + Math.abs((xRight - xToCompare) / xToCompare));
            return String.valueOf(xRight);
        } else {
            //System.out.println("Value f(xLeft)*f(xRight)>0");
            return "Failed, try using other method or change input parameters.";
        }
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using fixedPoint method
     *
     * @param x0       Start point
     * @param eps      Error
     * @param function ScalarFunction to estimate
     * @return Value of x, for which f(x)=0
     */
    public String fixedPoint(double x0, double eps, ScalarFunction function) {
        int counter = 1;
        double oldx;
        double x = x0 + function.f(x0);
        double blad;
        do {

            oldx = x;
            x = x + function.f(x);
            blad = Math.abs((x - oldx) / x);
            //System.out.println(counter++ + " x=" + x + " EpsA=" + blad);
        } while (blad >= eps);
        if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY) {
            return "Failed, try using other method or change input parameters.";
        } else if(function.f(x)>0.1 || function.f(x)<-0.1) return "Failed, try using other method or change input parameters.";
        else return String.valueOf(x);
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using fixedPoint method and comparing to number
     *
     * @param x0         Start point
     * @param eps        Error
     * @param function   ScalarFunction to estimate
     * @param xToCompare Value of x to compare to estimated value
     * @return Value of x, for which f(x)=0
     */
    public String fixedPoint(double x0, double eps, ScalarFunction function, double xToCompare) {
        int counter = 1;
        double oldx;
        double x = x0 + function.f(x0);
        double blad;
        do {

            oldx = x;
            x = x + function.f(x);
            blad = Math.abs((x - oldx) / x);
            //System.out.println(counter++ + " x=" + x + " EpsA=" + blad + " EpsT=" + Math.abs((x - xToCompare) / xToCompare));
        } while (blad >= eps);
        if (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY) {
            return "Failed, try using other method or change input parameters.";
        } else if(function.f(x)>0.1 || function.f(x)<-0.1) return "Failed, try using other method or change input parameters.";
        else return String.valueOf(x);
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using tangent method
     *
     * @param x0       Start point
     * @param eps      Error
     * @param function ScalarFunction to estimate
     * @return Value of x, for which f(x)=0
     */
    public String tangent(double x0, double eps, ScalarFunction function) {
        int counter = 1;
        double oldx;
        double x = x0 - function.f(x0) / (1 + function.f(x0) * firstTangent(function, x0));
        double blad;
        do {
            oldx = x;
            x = x - (function.f(x) / (1 + function.f(x) * firstTangent(function, x0)));
            blad = Math.abs((x - oldx) / x);
            //System.out.println(counter++ + " x=" + x + " EpsA=" + blad);
        } while (blad >= eps);
        if(function.f(x)>0.1 || function.f(x)<-0.1) return "Failed, try using other method or change input parameters.";
        else return String.valueOf(x);
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using tangent method and comparing to number
     *
     * @param x0         Start point
     * @param eps        Error
     * @param function   ScalarFunction to estimate
     * @param xToCompare Value of x to compare to estimated value
     * @return Value of x, for which f(x)=0
     */
    public String tangent(double x0, double eps, ScalarFunction function, double xToCompare) {
        int counter = 1;
        double oldx;
        double x = x0 - function.f(x0) / (1 + function.f(x0) * firstTangent(function, x0));
        double blad;
        do {
            oldx = x;
            x = x - (function.f(x) / (1 + function.f(x) * firstTangent(function, x0)));
            blad = Math.abs((x - oldx) / x);
            //System.out.println(counter++ + " x=" + x + " EpsA=" + blad + " EpsT=" + Math.abs((x - xToCompare) / xToCompare));
        } while (blad >= eps);
        if(function.f(x)>0.1 || function.f(x)<-0.1) return "Failed, try using other method or change input parameters.";
        else return String.valueOf(x);
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using secant method
     *
     * @param x0       Start of stating range
     * @param x1       End of starting range
     * @param eps      Error
     * @param function ScalarFunction to estimate
     * @return Value of x, for which f(x)=0
     */
    public String secant(double x0, double x1, double eps, ScalarFunction function) {
        int counter = 1;
        double xa;
        double xb = x1;
        double x = x1 - function.f(x1) * (x0 - x1) / (function.f(x0) - function.f(x1));
        double blad;
        do {
            xa = xb;
            xb = x;
            x = xb - function.f(xb) * (xa - xb) / (function.f(xa) - function.f(xb));
            blad = Math.abs((x - xb) / x);
            //System.out.println(counter++ + "xa=" + xa + " xb=" + xb + " x=" + x + " EpsA=" + blad);
        } while (blad >= eps);
        if(function.f(x)>0.1 || function.f(x)<-0.1) return "Failed, try using other method or change input parameters.";
        else return String.valueOf(x);
    }

    /**
     * Method counting x, for which f(x)=0 with error eps using secant method and comparing to number
     *
     * @param x0         Start of stating range
     * @param x1         End of starting range
     * @param eps        Error
     * @param function   ScalarFunction to estimate
     * @param xToCompare Value of x to compare to estimated value
     * @return Value of x, for which f(x)=0
     */
    public String secant(double x0, double x1, double eps, ScalarFunction function, double xToCompare) {
        int counter = 1;
        double xa;
        double xb = x1;
        double x = x1 - function.f(x1) * (x0 - x1) / (function.f(x0) - function.f(x1));
        double blad;
        do {
            xa = xb;
            xb = x;
            x = xb - function.f(xb) * (xa - xb) / (function.f(xa) - function.f(xb));
            blad = Math.abs((x - xb) / x);
            //System.out.println(counter++ + " xa=" + xa + " xb=" + xb + " x=" + x + " EpsA=" + blad + " EpsT=" + Math.abs((x - xToCompare) / xToCompare));
        } while (blad >= eps);
        if(function.f(x)>0.1 || function.f(x)<-0.1) return "Failed, try using other method or change input parameters.";
        else return String.valueOf(x);
    }

    /**
     * Method counting derivative value of ScalarFunction
     *
     * @param function ScalarFunction to count derivative
     * @param x        x
     * @return First derivative value of ScalarFunction in x
     */
    private double firstTangent(ScalarFunction function, double x) {
        return (function.f(x + Math.pow(10, -10) / 2.0) - function.f(x - Math.pow(10, -10) / 2.0)) / Math.pow(10, -10);
    }
}
