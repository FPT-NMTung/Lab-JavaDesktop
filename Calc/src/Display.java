
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author NMTung - Emily
 */
public class Display extends javax.swing.JFrame {

    /**
     * Creates new form Display
     */
    public Display() {
        initComponents();
    }

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";

    boolean enterFirstNumber = false;
    boolean enterSecondNumber = false;
    boolean enterOperator = false;
    boolean enterMemory = false;
    boolean enterSubMemory = false;
    boolean enterPlusMemory = false;
    boolean canDelete = false;
    boolean canNewCalc = true;

    String operator = "";
    String firstNumber = "0";
    String secondNumber = "0";
    String memory = "0";

    private void check() {
        System.out.println("================== check ==================");
        System.out.println("monitor: " + this.txtDisplay.getText());
        System.out.println("operator: " + this.operator);
        System.out.println("firstNumber: " + this.firstNumber);
        System.out.println("secondNumber: " + this.secondNumber);
        System.out.println("-------------------------------------------");
        System.out.println("enterFirstNumber: " + outTrueFalse(enterFirstNumber));
        System.out.println("enterSecondNumber: " + outTrueFalse(enterSecondNumber));
        System.out.println("enterOperator: " + outTrueFalse(enterOperator));
        System.out.println("enterMemory: " + outTrueFalse(enterMemory));
        System.out.println("canNewCalc: " + outTrueFalse(canNewCalc));
        System.out.println("-------------------------------------------");
    }

    private void reset() {
        txtDisplay.setText("0");

        enterFirstNumber = false;
        enterSecondNumber = false;
        enterOperator = false;
        canNewCalc = true;

        operator = "";
        firstNumber = "0";
        secondNumber = "0";
    }

    private String outTrueFalse(boolean value) {
        return value ? (ANSI_GREEN + value + ANSI_RESET) : (ANSI_RED + value + ANSI_RESET);
    }

    private void pressNumber(String number) {
        String textDisplay = txtDisplay.getText();
        // edit code after

        if (enterMemory || enterPlusMemory || enterSubMemory) {
            enterMemory = false;
            enterPlusMemory = false;
            enterSubMemory = false;
            textDisplay = "0";
        }

        if (canNewCalc) {
            enterFirstNumber = true;
            enterSecondNumber = false;
            enterOperator = false;
            canNewCalc = false;

            textDisplay = "0";
        }

        if (enterOperator) {
            enterFirstNumber = false;
            enterSecondNumber = true;
            enterOperator = false;
            canNewCalc = false;

            textDisplay = "0";
        }

        if (textDisplay.equals("0")) {
            textDisplay = number;
        } else {
            textDisplay += number;
        }

        if (enterFirstNumber) {
            firstNumber = textDisplay;
        } else if (enterSecondNumber) {
            secondNumber = textDisplay;
        }

        //edit code before
        txtDisplay.setText(textDisplay);
    }

    private void pressOperator(String operator) {

        if (canNewCalc) {
            enterFirstNumber = false;
            enterSecondNumber = false;
            enterOperator = true;
            canNewCalc = false;
        }

        if (enterFirstNumber) {
            enterFirstNumber = false;
            enterSecondNumber = false;
            enterOperator = true;
            canNewCalc = false;
        }

        if (enterSecondNumber) {
            BigDecimal first = new BigDecimal(firstNumber);
            BigDecimal second = new BigDecimal(secondNumber);

//            firstNumber = calculator(first, second);
            firstNumber = first.add(second).stripTrailingZeros().toPlainString();
//            secondNumber = "0";
            txtDisplay.setText(firstNumber);

            canNewCalc = false;
            enterSecondNumber = false;
            enterOperator = true;
        }

        this.operator = operator;
    }

    private void pressDot() {
        String textDisplay = this.txtDisplay.getText();

        if (enterMemory || enterPlusMemory || enterSubMemory) {
            enterMemory = false;
            enterPlusMemory = false;
            enterSubMemory = false;
            textDisplay = "0";
        }

        if (canNewCalc) {
            enterFirstNumber = true;
            enterSecondNumber = false;
            enterOperator = false;
            canNewCalc = false;

            textDisplay = "0";
        }

        if (enterOperator) {
            enterFirstNumber = false;
            enterSecondNumber = true;
            enterOperator = false;
            canNewCalc = false;

            textDisplay = "0";
        }

        if (!textDisplay.contains(".")) {
            textDisplay += ".";
        }

        this.txtDisplay.setText(textDisplay);
    }

    private void compute() {
        if (enterFirstNumber) {
            BigDecimal first = new BigDecimal(firstNumber);
            BigDecimal second = new BigDecimal(secondNumber);

            firstNumber = calculator(first, second);
//            firstNumber = first.add(second).stripTrailingZeros().toPlainString();
//            secondNumber = "0";
//            txtDisplay.setText(firstNumber);

            canNewCalc = true;
            enterFirstNumber = false;
        } else if (enterSecondNumber) {
            BigDecimal first = new BigDecimal(firstNumber);
            BigDecimal second = new BigDecimal(secondNumber);

            firstNumber = calculator(first, second);
//            firstNumber = first.add(second).stripTrailingZeros().toPlainString();
//            secondNumber = "0";
//            txtDisplay.setText(firstNumber);

            canNewCalc = true;
            enterSecondNumber = false;
        } else if (enterOperator) {
            BigDecimal first = new BigDecimal(firstNumber);
            BigDecimal second = new BigDecimal(firstNumber);

            firstNumber = calculator(first, second);
//            firstNumber = first.add(second).stripTrailingZeros().toPlainString();
            secondNumber = second.stripTrailingZeros().toPlainString();
//            txtDisplay.setText(firstNumber);

            canNewCalc = true;
            enterOperator = false;
        } else if (canNewCalc) {
            BigDecimal first = new BigDecimal(firstNumber);
            BigDecimal second = new BigDecimal(secondNumber);

            firstNumber = calculator(first, second);
//            firstNumber = first.add(second).stripTrailingZeros().toPlainString();
//            secondNumber = "0";
//            txtDisplay.setText(firstNumber);

            canNewCalc = true;
            enterSecondNumber = false;
        }
    }

    private String calculator(BigDecimal first, BigDecimal second) {
        boolean divineZero = false;
        BigDecimal total;
        switch (operator) {
            case "+":
                total = first.add(second);
                break;
            case "-":
                total = first.subtract(second);
                break;
            case "*":
                total = first.multiply(second).setScale(14, RoundingMode.HALF_UP);
                break;
            case "/":
                try {
                    total = first.divide(second, 15, RoundingMode.HALF_UP);
                } catch (Exception e) {
                    total = new BigDecimal("0");
                    txtDisplay.setText("Cannot divine by zero");
                    divineZero = true;
                    operator = "";
                }
                break;
            default:
                total = first.add(second);
                break;
        }
        if (!divineZero) {
            txtDisplay.setText(total.stripTrailingZeros().toPlainString());
        }
        divineZero = false;
        return total.stripTrailingZeros().toPlainString();
    }

    private void changeNegative() {
        String textDisplay = txtDisplay.getText();

        if (textDisplay.equals("0")) {
            return;
        }

        if (enterFirstNumber || enterSecondNumber) {

            if (textDisplay.contains("-")) {
                textDisplay = textDisplay.substring(1);
            } else {
                textDisplay = "-" + textDisplay;
            }

            if (enterFirstNumber) {
                firstNumber = textDisplay;
            }
            if (enterSecondNumber) {
                secondNumber = textDisplay;
            }
        }

        txtDisplay.setText(textDisplay);
    }

    private void oneDivNumber() {
        String textDisplay = txtDisplay.getText();
        String temp;

        try {
            temp = new BigDecimal(textDisplay).stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            return;
        }

        if (temp.equals("0")) {
            textDisplay = "Cannot divine by zero";
        } else {
            textDisplay = BigDecimal.ONE.divide(new BigDecimal(temp), 15, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            firstNumber = textDisplay;
        }

        canNewCalc = true;
        enterFirstNumber = false;
        enterSecondNumber = false;
        enterOperator = false;

        txtDisplay.setText(textDisplay);
    }

    private void divHun() {
        String textDisplay = txtDisplay.getText();
        String temp;

        try {
            temp = new BigDecimal(textDisplay).stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            return;
        }

        textDisplay = new BigDecimal(temp).divide(new BigDecimal(100), 15, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        firstNumber = textDisplay;

        canNewCalc = true;
        enterFirstNumber = false;
        enterSecondNumber = false;
        enterOperator = false;

        txtDisplay.setText(textDisplay);
    }

    private void sqrt() {
        String textDisplay = txtDisplay.getText();
        String temp;

        try {
            temp = new BigDecimal(textDisplay).stripTrailingZeros().toPlainString();

            if (Double.parseDouble(temp) < 0) {
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }

        textDisplay = new BigDecimal(Math.sqrt(Double.parseDouble(temp)) + "").stripTrailingZeros().toPlainString();
        firstNumber = textDisplay;

        canNewCalc = true;
        enterFirstNumber = false;
        enterSecondNumber = false;
        enterOperator = false;

        txtDisplay.setText(textDisplay);
    }

    private void clearMemory() {
        memory = "0";
    }

    private void showMemory() {
        txtDisplay.setText(memory);
        enterMemory = true;

        if (canNewCalc) {
            firstNumber = memory;
        }

        if (enterOperator) {
            secondNumber = memory;
        }

        if (enterFirstNumber) {
            firstNumber = memory;
        } else if (enterSecondNumber) {
            secondNumber = memory;
        }
    }

    private void plusMemory() {
        BigDecimal temp = new BigDecimal(memory);
        BigDecimal number = new BigDecimal(txtDisplay.getText());

        memory = temp.add(number).stripTrailingZeros().toPlainString();
        enterPlusMemory = true;
    }

    private void subMemory() {
        BigDecimal temp = new BigDecimal(memory);
        BigDecimal number = new BigDecimal(txtDisplay.getText());

        memory = temp.subtract(number).stripTrailingZeros().toPlainString();
        enterSubMemory = true;
    }

    private void delete() {
        String textDisplay = txtDisplay.getText();

        if (canDelete) {
            return;
        }
        if (enterOperator) {
            return;
        }
        if (canNewCalc) {
            return;
        }
        if (!textDisplay.equals("0")) {
            if (textDisplay.contains("-") && textDisplay.length() == 2) {
                textDisplay = "0";
            } else if (textDisplay.length() == 1) {
                textDisplay = "0";
            } else {
                textDisplay = textDisplay.substring(0, textDisplay.length() - 1);
            }
        }

        txtDisplay.setText(textDisplay);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDisplay = new javax.swing.JTextField();
        btnMc = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnMr = new javax.swing.JButton();
        btnMPlus = new javax.swing.JButton();
        btnMSub = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btnDiv = new javax.swing.JButton();
        btnSqrt = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btnMul = new javax.swing.JButton();
        btnHun = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btnSub = new javax.swing.JButton();
        btn1Div = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btnEq = new javax.swing.JButton();
        btnPlus = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btnDot = new javax.swing.JButton();
        btnChange = new javax.swing.JButton();
        lblClear = new javax.swing.JLabel();
        btnCheck = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        txtDisplay.setEditable(false);
        txtDisplay.setBackground(new java.awt.Color(255, 255, 255));
        txtDisplay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDisplay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDisplay.setText("0");

        btnMc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMc.setText("MC");
        btnMc.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMcActionPerformed(evt);
            }
        });

        btnMr.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMr.setText("MR");
        btnMr.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMrActionPerformed(evt);
            }
        });

        btnMPlus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMPlus.setText("M+");
        btnMPlus.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMPlusActionPerformed(evt);
            }
        });

        btnMSub.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMSub.setText("M-");
        btnMSub.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMSubActionPerformed(evt);
            }
        });

        btnDel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDel.setText("Del");
        btnDel.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btn7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn7.setText("7");
        btn7.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn8.setText("8");
        btn8.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn9.setText("9");
        btn9.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btnDiv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDiv.setText("/");
        btnDiv.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnDiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivActionPerformed(evt);
            }
        });

        btnSqrt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSqrt.setText("√");
        btnSqrt.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSqrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSqrtActionPerformed(evt);
            }
        });

        btn6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn6.setText("6");
        btn6.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btnMul.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMul.setText("*");
        btnMul.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnMul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMulActionPerformed(evt);
            }
        });

        btnHun.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnHun.setText("%");
        btnHun.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnHun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHunActionPerformed(evt);
            }
        });

        btn4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn4.setText("4");
        btn4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn5.setText("5");
        btn5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btnSub.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSub.setText("-");
        btnSub.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubActionPerformed(evt);
            }
        });

        btn1Div.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn1Div.setText("1/x");
        btn1Div.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn1Div.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1DivActionPerformed(evt);
            }
        });

        btn1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn1.setText("1");
        btn1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn2.setText("2");
        btn2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn3.setText("3");
        btn3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btnEq.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEq.setText("=");
        btnEq.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEqActionPerformed(evt);
            }
        });

        btnPlus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPlus.setText("+");
        btnPlus.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusActionPerformed(evt);
            }
        });

        btn0.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn0.setText("0");
        btn0.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });

        btnDot.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDot.setText(".");
        btnDot.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnDot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDotActionPerformed(evt);
            }
        });

        btnChange.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnChange.setText("+/-");
        btnChange.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        lblClear.setText("Clear all");
        lblClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClearMouseClicked(evt);
            }
        });

        btnCheck.setText("check");
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMul, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHun, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSub, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn1Div, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDot, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEq, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCheck))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnMc, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMr, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnMSub, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblClear))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMc, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMr, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMSub, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMul, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHun, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSub, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn1Div, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDot, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEq, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        pressNumber("1");
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        pressNumber("2");
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        pressNumber("3");
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        pressNumber("4");
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        pressNumber("5");
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        pressNumber("6");
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        pressNumber("7");
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        pressNumber("8");
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        pressNumber("9");
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
        pressNumber("0");
    }//GEN-LAST:event_btn0ActionPerformed

    private void btnDotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDotActionPerformed
        pressDot();
    }//GEN-LAST:event_btnDotActionPerformed

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
        check();
    }//GEN-LAST:event_btnCheckActionPerformed

    private void btnEqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEqActionPerformed
        compute();
    }//GEN-LAST:event_btnEqActionPerformed

    private void btnPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusActionPerformed
        pressOperator("+");
    }//GEN-LAST:event_btnPlusActionPerformed

    private void btnSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubActionPerformed
        pressOperator("-");
    }//GEN-LAST:event_btnSubActionPerformed

    private void btnMulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMulActionPerformed
        pressOperator("*");
    }//GEN-LAST:event_btnMulActionPerformed

    private void btnDivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivActionPerformed
        pressOperator("/");
    }//GEN-LAST:event_btnDivActionPerformed

    private void lblClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClearMouseClicked
        reset();
    }//GEN-LAST:event_lblClearMouseClicked

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        changeNegative();
    }//GEN-LAST:event_btnChangeActionPerformed

    private void btn1DivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1DivActionPerformed
        oneDivNumber();
    }//GEN-LAST:event_btn1DivActionPerformed

    private void btnHunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHunActionPerformed
        divHun();
    }//GEN-LAST:event_btnHunActionPerformed

    private void btnSqrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSqrtActionPerformed
        sqrt();
    }//GEN-LAST:event_btnSqrtActionPerformed

    private void btnMcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMcActionPerformed
        clearMemory();
    }//GEN-LAST:event_btnMcActionPerformed

    private void btnMrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMrActionPerformed
        showMemory();
    }//GEN-LAST:event_btnMrActionPerformed

    private void btnMPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMPlusActionPerformed
        plusMemory();
    }//GEN-LAST:event_btnMPlusActionPerformed

    private void btnMSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMSubActionPerformed
        subMemory();
    }//GEN-LAST:event_btnMSubActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delete();
    }//GEN-LAST:event_btnDelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Display().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn1Div;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnDiv;
    private javax.swing.JButton btnDot;
    private javax.swing.JButton btnEq;
    private javax.swing.JButton btnHun;
    private javax.swing.JButton btnMPlus;
    private javax.swing.JButton btnMSub;
    private javax.swing.JButton btnMc;
    private javax.swing.JButton btnMr;
    private javax.swing.JButton btnMul;
    private javax.swing.JButton btnPlus;
    private javax.swing.JButton btnSqrt;
    private javax.swing.JButton btnSub;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblClear;
    private javax.swing.JTextField txtDisplay;
    // End of variables declaration//GEN-END:variables

}
