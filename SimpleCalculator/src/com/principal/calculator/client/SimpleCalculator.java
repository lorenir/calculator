package com.principal.calculator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class SimpleCalculator implements IsWidget, EntryPoint {

	private FramedPanel widget;

	@Override
	public Widget asWidget() {
		if (widget == null) {

			// FlexTable for Display
			FlexTable tableDisplay = new FlexTable();
			tableDisplay.setCellSpacing(1);
			tableDisplay.setCellPadding(1);
			tableDisplay.setBorderWidth(0);
			
			TextField display = new TextField();
			display.setText("0");
			display.setReadOnly(true);
			display.setDirection(Direction.RTL);
			display.setSize("172px", "100%");
			
			tableDisplay.setWidget(0, 0, display);			
			
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
					if (txt.equals("1")) {
						Info.display("Click Number", txt + " clicked");
					} else if (txt.equals("2")) {
						Info.display("Click Number", txt + " clicked");
					} else if (txt.equals("3")) {
						Info.display("Click Number", txt + " clicked");
					} else {
						Info.display("Click Number", txt + " clicked");
					}
				} // onSelect
			}; // SelectHandlerNumber

			SelectHandler handlerOperator = new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					TextButton btn = (TextButton) event.getSource();
					String txt = btn.getText();
					Info.display("Click Operator", txt + " clicked");
				} // onSelect
			}; // SelectHandlerOperator

			TextButton newButton = new TextButton("CE", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(0, 0, newButton);
			
			newButton = new TextButton("C", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(0, 1, newButton);

			newButton = new TextButton("%", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(0, 2, newButton);

			newButton = new TextButton("+", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(0, 3, newButton);

			
			newButton = new TextButton("7", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(1, 0, newButton);
			
			newButton = new TextButton("8", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(1, 1, newButton);
			
			newButton = new TextButton("9", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(1, 2, newButton);
			
			newButton = new TextButton("-", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(1, 3, newButton);
						
			
			newButton = new TextButton("4", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(2, 0, newButton);
			
			newButton = new TextButton("5", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(2, 1, newButton);
			
			newButton = new TextButton("6", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(2, 2, newButton);
			
			newButton = new TextButton("x", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(2, 3, newButton);
						
			
			newButton = new TextButton("1", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(3, 0, newButton);
			
			newButton = new TextButton("2", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(3, 1, newButton);
			
			newButton = new TextButton("3", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(3, 2, newButton);
			
			newButton = new TextButton("/", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(3, 3, newButton);
						
			
			newButton = new TextButton("0", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(4, 0, newButton);
			
			newButton = new TextButton(".", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(4, 1, newButton);
			
			newButton = new TextButton("+/-", handlerNumber);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(4, 2, newButton);
			
			newButton = new TextButton("=", handlerOperator);
			newButton.setSize("40px", "40px");
			tableButtons.setWidget(4, 3, newButton);

			// VerticalLayoutContainer for Buttons
			VerticalLayoutContainer simpleCalculatorContainer = new VerticalLayoutContainer();
			simpleCalculatorContainer.add(tableDisplay, new VerticalLayoutData(1, .2));
			simpleCalculatorContainer.add(tableButtons, new VerticalLayoutData(1, .8));
			
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