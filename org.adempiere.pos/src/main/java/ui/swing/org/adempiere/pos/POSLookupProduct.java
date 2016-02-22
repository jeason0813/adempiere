/** ****************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2016 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 * ****************************************************************************/

package org.adempiere.pos;

import org.adempiere.pos.service.CPOS;
import org.adempiere.util.StringUtils;
import org.compiere.apps.ADialog;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;


/**
 * Component allows to show product lookup search key , name , quantity available , price standard and price list
 * eEvolution author Victor Perez <victor.perez@e-evolution.com>, Created by e-Evolution on 24/01/16.
 */
public class POSLookupProduct implements ActionListener, KeyListener {

    private POSLookupProductInterface lookupProductInterface = null;
    private POSTextField fieldProductName = null;
    private long lastKeyboardEvent = 0;
    private boolean searched = false;
    private boolean selectLock = false;
    private JComboBox<KeyNamePair> component = null;
    private Integer priceListVersionId = 0;
    private Integer warehouseId = 0;
    private String fill = StringUtils.repeat(" " , 400);
    static private Integer PRODUCT_VALUE_LENGTH = 14;
    static private Integer PRODUCT_NAME_LENGTH = 50;
    static private Integer QUANTITY_LENGTH = 16;

    private String separator = "|";
    private String productValueTitle   = StringUtils.trunc(Msg.parseTranslation(Env.getCtx() , "@ProductValue@") + fill , PRODUCT_VALUE_LENGTH );
    private String productTitle        = StringUtils.trunc(Msg.parseTranslation(Env.getCtx() , "@M_Product_ID@") + fill , PRODUCT_NAME_LENGTH );
    private String availableTitle      = StringUtils.trunc(Msg.parseTranslation(Env.getCtx() , "@QtyAvailable@") + fill , QUANTITY_LENGTH );
    private String priceStdTitle       = StringUtils.trunc(Msg.parseTranslation(Env.getCtx() , "@PriceStd@")     + fill , QUANTITY_LENGTH );
    private String priceListTile       = StringUtils.trunc(Msg.parseTranslation(Env.getCtx() , "@PriceList@")    + fill , QUANTITY_LENGTH );
    private String title = "";



    public POSLookupProduct (POSLookupProductInterface lookupProductInterface, POSTextField fieldProductName, long lastKeyboardEvent)
    {
        this.lookupProductInterface = lookupProductInterface;
        this.fieldProductName = fieldProductName;
        this.lastKeyboardEvent = lastKeyboardEvent;
    }

    public void setLastKeyboardEvent(long lastKeyboardEvent)
    {
        this.lastKeyboardEvent = lastKeyboardEvent;
    }

    public void setFillingComponent(JComboBox<KeyNamePair> component)
    {
        this.component = component;
        component.addActionListener(this);
        component.addKeyListener(this);
        char[] charArray = new char[200];
        Arrays.fill(charArray,' ');
        this.fill = new String(charArray);
        this.title = new StringBuffer()
                .append(productValueTitle).append(separator)
                .append(productTitle).append(separator)
                .append(availableTitle).append(separator)
                .append(priceStdTitle).append(separator)
                .append(priceListTile).toString();
        component.addItem(new KeyNamePair(0, this.title));
    }

    public void setPriceListVersionId(int priceListVersionId)
    {
        this.priceListVersionId = priceListVersionId;
    }

    public void setWarehouseId(int warehouseId)
    {
        this.warehouseId = warehouseId;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource()==component
        && actionEvent.getModifiers() == 16
        && actionEvent.getSource() != lookupProductInterface.getProductTimer())
            captureProduct();

        if(actionEvent.getSource()== lookupProductInterface.getProductTimer())
        {
            long now = System.currentTimeMillis();

            if( (now - lastKeyboardEvent) > 500
            && !searched && fieldProductName.getText() != null
            && fieldProductName.getText().length() > 2)
            {
                executeQuery();
            }
            else if(!searched && (fieldProductName.getText()== null ||  fieldProductName.getText().length() == 0))
            {
                component.hidePopup();
                component.removeAllItems();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)  {
        if(keyEvent.getKeyCode()==40 && keyEvent.getSource()== fieldProductName) // Key down on product text field
        {
            component.requestFocus();
        }
        else if (keyEvent.getSource()== fieldProductName) //writing product name or value
        {
            searched = false;
            this.lastKeyboardEvent = System.currentTimeMillis();
            if (lookupProductInterface.getProductTimer() != null)
                ((javax.swing.Timer)lookupProductInterface.getProductTimer()).restart();
        }
        else if(keyEvent.getKeyCode()==10 && keyEvent.getSource()==component) //Enter on component field
            captureProduct();
        if (KeyEvent.VK_TAB == keyEvent.getKeyCode()) {
            fieldProductName.setPlaceholder(fieldProductName.getText());
            try {
                lookupProductInterface.findProduct(false);
            } catch (Exception exception) {
                ADialog.error(0 , null , exception.getLocalizedMessage());
            }
            lookupProductInterface.quantityRequestFocus();
            fieldProductName.setText("");
            return;
        }
        if (KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
            fieldProductName.setPlaceholder(fieldProductName.getText());
            try {
                lookupProductInterface.findProduct(true);
            } catch (Exception exception) {
                ADialog.error(0 , null , exception.getLocalizedMessage());
            }
            lookupProductInterface.quantityRequestFocus();
            fieldProductName.setText("");
            return;
        }
    }

    public void captureProduct()
    {
        KeyNamePair item = (KeyNamePair) component.getSelectedItem();
        if(item!=null && !selectLock)
        {
            String productValue = DB.getSQLValueString(null , "SELECT Value FROM M_Product p WHERE M_Product_ID=?", item.getKey());
            fieldProductName.setPlaceholder(productValue);
            try {
                lookupProductInterface.findProduct(true);
            } catch (Exception exception) {
                ADialog.error(0 , null , exception.getLocalizedMessage());
            }
            component.removeAllItems();
            fieldProductName.setText("");
        }

    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }


    private void executeQuery()
    {
            searched = true;
            component.removeAllItems();
            component.addItem(new KeyNamePair(0, title));
            selectLock = true;
            for (java.util.Vector<Object> columns : CPOS.getQueryProduct(fieldProductName.getText(), warehouseId, priceListVersionId))
            {
                Integer productId = (Integer) columns.elementAt(0);
                String productValue = (String) columns.elementAt(1);
                String productName = (String) columns.elementAt(2);
                String qtyAvailable = (String) columns.elementAt(3);
                String priceStd = (String) columns.elementAt(4);
                String priceList = (String) columns.elementAt(5);
                String  line = new StringBuilder()
                        .append(StringUtils.trunc(productValue + fill , PRODUCT_VALUE_LENGTH )).append(separator)
                        .append(StringUtils.trunc(productName + fill , PRODUCT_NAME_LENGTH )).append(separator)
                        .append(StringUtils.trunc(qtyAvailable + fill , QUANTITY_LENGTH)).append(separator)
                        .append(StringUtils.trunc(priceStd + fill, QUANTITY_LENGTH )).append(separator)
                        .append(StringUtils.trunc(priceList + fill, QUANTITY_LENGTH )).toString();
                component.addItem(new KeyNamePair(productId, line));
            }

        component.showPopup();
        selectLock = false;
    }
}