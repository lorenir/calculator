package com.principal.calculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.principal.calculator.client.controllers.Calculator;
import com.principal.calculator.client.entities.Symbol;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FloatField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class SimpleCalculator implements IsWidget, EntryPoint {

	private FramedPanel widget;
	private Calculator calculator;
	private FloatField calculatorDisplay;

	@Override
	public Widget asWidget() {
		if (calculator == null) {
			calculator = new Calculator();
		}
		if (widget == null) {

			// FlexTable for Display
			FlexTable tableDisplay = new FlexTable();
			tableDisplay.setCellSpacing(1);
			tableDisplay.setCellPadding(1);
			tableDisplay.setBorderWidth(0);

			calculatorDisplay = new FloatField();
			calculatorDisplay.setText("0");
			calculatorDisplay.setReadOnly(true);
			//calculatorDisplay.setLayoutData(new textData());;
			calculatorDisplay.setDirection(Direction.RTL);
			calculatorDisplay.setSize("172px", "100%");

			tableDisplay.setWidget(0, 0, calculatorDisplay);

			// FlexTable for Buttons
			FlexTable tableButtons = new FlexTable();
			tableButtons.setCellSpacing(1);
			tableButtons.setCellPadding(1);
			tableButtons.setBorderWidth(0);

			SelectHandler handlerNumber = new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					TextButton btn = (TextButton) event.getSource();
					String txt = btn.getText();
					String displayText = calculator.AddSymbol(txt);

					calculatorDisplay.setText(displayText);
					
					Info.display("Digit", "Display: " + displayText + " op1: " + calculator.getOp1().getValue() + " op2: "
							+ calculator.getOp2().getValue() + " operation: " + calculator.getOperation()
							+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState());
				} // onSelect
			}; // handlerNumber

			SelectHandler handlerOperator = new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					TextButton btn = (TextButton) event.getSource();
					String txt = btn.getText();
					String displayText = calculator.Operate(txt);

					calculatorDisplay.setText(displayText);
					
					Info.display("Operator", "Display: " + displayText + " op1: " + calculator.getOp1().getValue() + " op2: "
							+ calculator.getOp2().getValue() + " operation: " + calculator.getOperation()
							+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState());				} // onSelect
			}; // handlerOperator

			// Button table creation
			final String buttonWidth = "40px";
			final String buttonHigh = "40px";
			final int tableWidth = 4;
			int currentRow = 0;
			int currentCol = 0;
			TextButton newButton = new TextButton();

			for (Symbol currentSymbol : Symbol.values()) {
				newButton = new TextButton(currentSymbol.getSymbol(),
						currentSymbol.isSymbol() ? handlerNumber : handlerOperator);
				newButton.setSize(buttonWidth, buttonHigh);
				currentRow = currentSymbol.ordinal() % tableWidth;
				tableButtons.setWidget(currentCol, currentRow, newButton);
				currentCol += (currentRow == (tableWidth - 1)) ? 1 : 0;
			}

			// Bar menu for additional operations
			final String btn1 = "to Binary";
			final String btn2 = "Bin History";
			final String btn3 = "See all vars";
			
			SelectHandler handlerSpecialOperator = new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					TextButton btn = (TextButton) event.getSource();
					String action = btn.getText();
					if (action == btn1) {
						//String displayText = action; //calculator.SpecialOperate(action);
						calculatorDisplay.setText(action+"...");
					} else if (action == btn2) {
						//String displayText = action; //calculator.SpecialOperate(action);
						calculatorDisplay.setText(action+"...");
					} else if (action == btn3) {
						 Dialog d = new Dialog();
						 d.setModal(true);
						 d.setHeadingText("Debug variables");
						 d.setBorders(true);
						 d.setBodyBorder(true);
						 d.setWidget(new HTML("op1: " + calculator.getOp1().getValue() + " op2: "
									+ calculator.getOp1().getValue() + " operation: " + calculator.getOperation()
									+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState()));
						 d.setBodyStyle("backgroundColor:white;padding:13px;");
						 d.setPixelSize(300, 150);
						 d.setHideOnButtonClick(true);
						 d.setPredefinedButtons(PredefinedButton.OK);
						 d.setButtonAlign(BoxLayoutPack.CENTER);
						 d.show();
					}
										
					Info.display("Special Operator", "Display: " + action + " op1: " + calculator.getOp1().getValue() + " op2: "
							+ calculator.getOp1().getValue() + " operation: " + calculator.getOperation()
							+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState());				} // onSelect
			}; // handlerSpecialOperator

			ContentPanel menuPanel = new ContentPanel();
			menuPanel.addButton(new TextButton(btn1, handlerSpecialOperator));
			menuPanel.addButton(new TextButton(btn2, handlerSpecialOperator));
			menuPanel.addButton(new TextButton(btn3, handlerSpecialOperator));
			menuPanel.setHeaderVisible(false);
			
			// VerticalLayoutContainer for Buttons
			VerticalLayoutContainer simpleCalculatorContainer = new VerticalLayoutContainer();
			simpleCalculatorContainer.add(tableDisplay, new VerticalLayoutData(1, .1));
			simpleCalculatorContainer.add(tableButtons, new VerticalLayoutData(1, .8));
			simpleCalculatorContainer.add(menuPanel, new VerticalLayoutData(1, .1));
			
			// FramedPanel
			widget = new FramedPanel();
			widget.setHeadingText("Calculator");
			widget.add(simpleCalculatorContainer, new MarginData(5));
			widget.setPixelSize(200, 300);
		} // if

		return widget;
	}

	@Override
	public void onModuleLoad() {
		// FramedPanel --> VerticalLayoutContainer --> FlexTable
		RootPanel.get().add(asWidget());
	}

} // SimpleCalculator