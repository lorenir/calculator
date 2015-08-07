package com.principal.calculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.principal.calculator.client.controllers.Calculator;
import com.principal.calculator.client.entities.Symbol;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FloatField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class SimpleCalculator implements IsWidget, EntryPoint {

	private final ConversionServiceAsync conversionService = GWT.create(ConversionService.class);

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
			tableDisplay.setCellSpacing(4);
			tableDisplay.setCellPadding(2);
			tableDisplay.setBorderWidth(0);

			calculatorDisplay = new FloatField();
			calculatorDisplay.setText("0");
			calculatorDisplay.setReadOnly(true);
			calculatorDisplay.setDirection(Direction.RTL);
			calculatorDisplay.setSize("178px", "100%");

			tableDisplay.setWidget(0, 0, calculatorDisplay);

			// FlexTable for Buttons
			FlexTable tableButtons = new FlexTable();
			tableButtons.setCellSpacing(4);
			tableButtons.setCellPadding(2);
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
				}
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
							+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState());				
					}
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
			final String txtMenuOptionConvert = "Decimal to Binary";
			final String txtMenuOptionHistory = "Binary Conversion History...";
			final String txtMenuOptionDebug = "See all internal calculator vars...";
			
			SelectionHandler<Item> handler = new SelectionHandler<Item>() {
			        @Override
			        public void onSelection(SelectionEvent<Item> event) {
			          if (event.getSelectedItem() instanceof MenuItem) {
			            MenuItem item = (MenuItem) event.getSelectedItem();
			            String action = item.getText();
						if (action == txtMenuOptionConvert) {
							convert(calculatorDisplay.getText());
							//String displayText = action; //calculator.SpecialOperate(action);
							calculatorDisplay.setText(action+"...");
						} else if (action == txtMenuOptionHistory) {
							//String displayText = action; //calculator.SpecialOperate(action);
							calculatorDisplay.setText(action+"...");
						} else if (action == txtMenuOptionDebug) {
							 MessageBox mb = new MessageBox("Debug variables:");
							 mb.setMessage("op1: [" + calculator.getOp1().getValue() + "] - op2: ["
								+ calculator.getOp2().getValue() + "] - operation: [" + calculator.getOperation()
								+ "] - finalEdition: [" + calculator.isFinalOperator() + "] - state: [" + calculator.getState() + "]");
							 mb.show();
						}
			          }
			        }
			};
			 			 
			Menu subMenuConvert = new Menu();
			subMenuConvert.addSelectionHandler(handler);
			subMenuConvert.add(new MenuItem(txtMenuOptionConvert));
			subMenuConvert.add(new MenuItem(txtMenuOptionHistory));
			MenuBarItem menuBarConvert = new MenuBarItem("Convert", subMenuConvert);
			 
			Menu subMenuDebug = new Menu();
			subMenuDebug.addSelectionHandler(handler);
			subMenuDebug.add(new MenuItem(txtMenuOptionDebug));
			MenuBarItem menuBarDebug = new MenuBarItem("Debug", subMenuDebug);
			 			 
			MenuBar menuBar = new MenuBar();
			menuBar.addStyleName(ThemeStyles.get().style().borderBottom());
			menuBar.add(menuBarConvert);
			menuBar.add(menuBarDebug);
			 
			// VerticalLayoutContainer for Buttons
			VerticalLayoutContainer simpleCalculatorContainer = new VerticalLayoutContainer();
			simpleCalculatorContainer.add(menuBar, new VerticalLayoutData(-1, .1));
			simpleCalculatorContainer.add(tableDisplay, new VerticalLayoutData(-1, .15));
			simpleCalculatorContainer.add(tableButtons, new VerticalLayoutData(1, .75));
			
			// FramedPanel
			widget = new FramedPanel();
			widget.setHeadingText("Calculator");
			widget.setBorders(true);
			widget.setBodyBorder(true);
			widget.setPixelSize(200, 340);
			widget.setLayoutData(new MarginData(-1));
			widget.add(simpleCalculatorContainer, new MarginData(-1));
		} // if

		return widget;
	}

	@Override
	public void onModuleLoad() {
		// FramedPanel --> VerticalLayoutContainer --> FlexTable
		RootPanel.get().add(asWidget());
	}
	
	private void convert(String decimalNumber) {
		// First, we validate the input.
		int pos = decimalNumber.indexOf(".");
		String integerPart = decimalNumber;
		if (pos>0){
			 integerPart = decimalNumber.substring(0,decimalNumber.indexOf("."));
		}
		if (integerPart == null || integerPart.isEmpty()){
			calculatorDisplay.setText("ERROR");			
			return;
		}
		
		conversionService.conversion(Long.parseLong(integerPart), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				calculatorDisplay.setText("Conexion Error");			
			}
			public void onSuccess(String result) {
				calculatorDisplay.setText(result);
			}
		});
	}

} // SimpleCalculator