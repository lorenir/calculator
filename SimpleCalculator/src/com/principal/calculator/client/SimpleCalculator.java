package com.principal.calculator.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.principal.calculator.client.controllers.Calculator;
import com.principal.calculator.client.entities.Symbol;
import com.principal.calculator.shared.domain.BinaryConversionRegister;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
//import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class SimpleCalculator implements IsWidget, EntryPoint {

	private final ConversionServiceAsync conversionServiceAsync = GWT.create(ConversionService.class);

	private FramedPanel widget;
	private Calculator calculator;
	private TextField calculatorDisplay;


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

			KeyDownHandler keyHandler = new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					int code = event.getNativeKeyCode();
					String displayText = "NaN";
					if (code>=48 && code<=57) {
						if (!event.isShiftKeyDown()) {
							displayText = calculator.AddSymbol(String.valueOf(code-48));
						} else if (code==48) {
							displayText = calculator.Operate(Symbol.EQUAL.getSymbol());
						} else if (code==53) {
							displayText = calculator.Operate(Symbol.PORC.getSymbol());
						} else if (code==55) {
							displayText = calculator.Operate(Symbol.DIV.getSymbol());
						}
					} else if (code>=96 && code<=105) {
						displayText = calculator.AddSymbol(String.valueOf(code-96));
					} else if (code==110 || code==188 || code==190) {
						displayText = calculator.AddSymbol(Symbol.DEC_SEPARATOR.getSymbol());
					} else if (code==78) {
						displayText = calculator.AddSymbol(Symbol.SIGN.getSymbol());
											
					} else if (code==8 | code==46) {
						displayText = calculator.Operate(Symbol.CLEAN_ELEMENT.getSymbol());
					} else if (code==27 || code==67) {
						displayText = calculator.Operate(Symbol.CLEAN.getSymbol());
					} else if (code==13 || code==61) {
						displayText = calculator.Operate(Symbol.EQUAL.getSymbol());
					} else if (code==106 || code==170 || code==88) {
						displayText = calculator.Operate(Symbol.MULT.getSymbol());
					} else if (code==107) {
						displayText = calculator.Operate(Symbol.PLUS.getSymbol());
					} else if (code==171) {
						displayText = (event.isShiftKeyDown())?calculator.Operate(Symbol.MULT.getSymbol()):calculator.Operate(Symbol.PLUS.getSymbol());
					} else if (code==109 || code==173) {
						displayText = calculator.Operate(Symbol.MINUS.getSymbol());
					} else if (code==111 || code==47) {
						displayText = calculator.Operate(Symbol.DIV.getSymbol());
					} else if (code==80 || code==37) {
						displayText = calculator.Operate(Symbol.PORC.getSymbol());
					}
					
					if (!displayText.equals("NaN"))calculatorDisplay.setText(displayText);

//					// Debug
//					Info.display("keyDown", 
//							String.valueOf(event.getNativeKeyCode())+ ":" +
//							String.valueOf(event.hashCode())+ ":" +
//							String.valueOf(event.isShiftKeyDown())
//							);				
				}
			}; // handlerNumber

			calculatorDisplay = new TextField();
			calculatorDisplay.setText("0");
			calculatorDisplay.setReadOnly(true);
			calculatorDisplay.setAllowTextSelection(false);
			calculatorDisplay.setSize("178px", "100%");
			calculatorDisplay.addKeyDownHandler(keyHandler);
			
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
					calculatorDisplay.focus();

//					// Debug info
//					Info.display("Digit", "Display: " + displayText + " op1: " + calculator.getOp1().getValue()
//							+ " op2: " + calculator.getOp2().getValue() + " operation: " + calculator.getOperation()
//							+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState());
				}
			}; // handlerNumber

			SelectHandler handlerOperator = new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					TextButton btn = (TextButton) event.getSource();
					String txt = btn.getText();
					String displayText = calculator.Operate(txt);

					calculatorDisplay.setText(displayText);
					calculatorDisplay.focus();

//					// Debug info
//					Info.display("Operator", "Display: " + displayText + " op1: " + calculator.getOp1().getValue()
//							+ " op2: " + calculator.getOp2().getValue() + " operation: " + calculator.getOperation()
//							+ " finalEdition: " + calculator.isFinalOperator() + " state: " + calculator.getState());
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
			final String txtMenuOptionConvert = "Decimal to Binary...";
			final String txtMenuOptionHistory = "Show Binary Conversion History...";
			final String txtMenuOptionDebug = "Show Internal calculator vars...";

			SelectionHandler<Item> handlerSpecialOperator = new SelectionHandler<Item>() {
				@Override
				public void onSelection(SelectionEvent<Item> event) {
					if (event.getSelectedItem() instanceof MenuItem) {
						MenuItem item = (MenuItem) event.getSelectedItem();
						String action = item.getText();
						if (action == txtMenuOptionConvert) {
							convertToBinary(calculatorDisplay.getText());
						} else if (action == txtMenuOptionHistory) {
							showConversionHistory();
						} else if (action == txtMenuOptionDebug) {
							MessageBox mb = new MessageBox("Debug variables:");
							mb.setMessage("op1: [" + calculator.getOp1().getValue() + "] - op2: ["
									+ calculator.getOp2().getValue() + "] - operation: [" + calculator.getOperation()
									+ "] - finalEdition: [" + calculator.isFinalOperator() + "] - state: ["
									+ calculator.getState() + "]");
							mb.show();
						}
					}
					calculatorDisplay.focus();
				}
			}; // handlerSpecialOperator

			Menu subMenuConvert = new Menu();
			subMenuConvert.addSelectionHandler(handlerSpecialOperator);
			subMenuConvert.add(new MenuItem(txtMenuOptionConvert));
			subMenuConvert.add(new MenuItem(txtMenuOptionHistory));
			MenuBarItem menuBarConvert = new MenuBarItem("Convert", subMenuConvert);

			Menu subMenuDebug = new Menu();
			subMenuDebug.addSelectionHandler(handlerSpecialOperator);
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
	}  // asWidget

	@Override
	public void onModuleLoad() {
		// FramedPanel --> VerticalLayoutContainer --> FlexTable
		RootPanel.get().add(asWidget());
		calculatorDisplay.focus();
	}

	
	private void convertToBinary(String decimalNumber) {
		int pos = decimalNumber.indexOf(".");

		String integerPart = decimalNumber;
		if (pos > 0) {
			integerPart = decimalNumber.substring(0, decimalNumber.indexOf("."));
		}
		if (integerPart == null || integerPart.isEmpty()) {
			MessageBox mb = new MessageBox("Converting decimal to binary:");
			mb.setMessage("ERROR");
			mb.show();
			return;
		}

		conversionServiceAsync.conversion(Long.parseLong(integerPart), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				MessageBox mb = new MessageBox("Converting decimal to binary:");
				mb.setMessage("Conexion Error");
				mb.show();
			}

			public void onSuccess(String result) {
				MessageBox mb = new MessageBox("Converting decimal to binary:");
				mb.setMessage(calculatorDisplay.getText() + ":" + result);
				mb.show();
			}
		});
	} // convert

	// just to show the converter feature
	interface PlaceProperties extends PropertyAccess<BinaryConversionRegister> {
		@Path("id")
		ModelKeyProvider<BinaryConversionRegister> key();
		ValueProvider<BinaryConversionRegister, String> decimal();
		ValueProvider<BinaryConversionRegister, String> binary();
		ValueProvider<BinaryConversionRegister, Date> dateConversion();
	}

	private void showConversionHistory() {

		conversionServiceAsync.conversionHistory(new AsyncCallback<List<BinaryConversionRegister>>() {
			public void onFailure(Throwable caught) {
				MessageBox mb = new MessageBox("Converting decimal to binary:");
				mb.setMessage("Conexion Error");
				mb.show();
			}

			@Override
			public void onSuccess(List<BinaryConversionRegister> result) {
				final PlaceProperties properties = GWT.create(PlaceProperties.class);
				Grid<BinaryConversionRegister> grid;

				// Create Grid model
				ColumnConfig<BinaryConversionRegister, String> decimalColumn = new ColumnConfig<BinaryConversionRegister, String>(properties.decimal(), 110, "Decimal");
				ColumnConfig<BinaryConversionRegister, String> binarylColumn = new ColumnConfig<BinaryConversionRegister, String>(properties.binary(), 150, "Binary");
				ColumnConfig<BinaryConversionRegister, Date> dateColumn = new ColumnConfig<BinaryConversionRegister, Date>(properties.dateConversion(), 130, "Date");
				dateColumn.setCell(new DateCell(DateTimeFormat.getFormat("dd/mm/yyyy hh:mm aaa")));

				List<ColumnConfig<BinaryConversionRegister, ?>> conversioRegisterList = new ArrayList<ColumnConfig<BinaryConversionRegister, ?>>();
				conversioRegisterList.add(decimalColumn);
				conversioRegisterList.add(binarylColumn);
				conversioRegisterList.add(dateColumn);

				ColumnModel<BinaryConversionRegister> columns = new ColumnModel<BinaryConversionRegister>(conversioRegisterList);
				ListStore<BinaryConversionRegister> history = new ListStore<BinaryConversionRegister>(properties.key());

				// Load Data
				for (BinaryConversionRegister bcr : result) {					
					history.add(bcr);
				}
				
				// Create Grid
				grid = new Grid<BinaryConversionRegister>(history, columns);
				grid.getView().setAutoExpandColumn(decimalColumn);

				// Create Dialog
				VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
				verticalLayoutContainer.setBorders(true);
				verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 1));

				Dialog historyDialog = new Dialog();
				historyDialog.setBodyBorder(false);
				historyDialog.setHeadingText("Binary Conversions History");
				historyDialog.setWidth(400);
				historyDialog.setHeight(300);
				historyDialog.setHideOnButtonClick(true);
				historyDialog.setModal(true);
				historyDialog.add(verticalLayoutContainer);
				historyDialog.show();
			}
		});

	} // showConversionHistory

} // SimpleCalculator