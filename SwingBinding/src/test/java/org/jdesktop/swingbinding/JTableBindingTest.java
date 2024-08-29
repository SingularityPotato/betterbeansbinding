/***********************************************************************************************************************
 *
 * BetterBeansBinding - keeping JavaBeans in sync
 * ==============================================
 *
 * Copyright (C) 2009 by Tidalwave s.a.s. (http://www.tidalwave.it)
 * http://betterbeansbinding.kenai.com
 *
 * This is derived work from BeansBinding: http://beansbinding.dev.java.net
 * BeansBinding is copyrighted (C) by Sun Microsystems, Inc.
 *
 ***********************************************************************************************************************
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 ***********************************************************************************************************************
 *
 * $Id$
 *
 **********************************************************************************************************************/
package org.jdesktop.swingbinding;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingbinding.mock.DoubleTableCellRenderer;
import org.jdesktop.swingbinding.mock.IntegerTableCellRenderer;
import org.jdesktop.swingbinding.mock.MockBean;
import org.jdesktop.swingbinding.mock.SpinnerTableCellEditor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

/**
 * @author fritz
 */
public class JTableBindingTest {

    private static final String PROPERTY_SELECTED = "selectedElement_IGNORE_ADJUSTING";

    private String property( String prop ) {
        return PROPERTY_SELECTED + "." + prop;
    }

    @Test
    public void test_jTableOnSelection() {
        final List<MockBean> beans = new ArrayList<>();
        beans.add( new MockBean( 1, 11.1, "First row" ) );
        beans.add( new MockBean( 2, 22.2, "Second row" ) );
        beans.add( new MockBean( 3, 33.3, "Third row" ) );
        beans.add( new MockBean( 4, 44.4, "Fourth row" ) );

        final JTable table = new JTable();
        final IntegerTableCellRenderer integerTableCellRenderer = new IntegerTableCellRenderer();
        final DoubleTableCellRenderer doubleTableCellRenderer = new DoubleTableCellRenderer();
        final SpinnerTableCellEditor spinnerTableCellEditor = new SpinnerTableCellEditor();

        final JTableBinding<MockBean, List<MockBean>, JTable> tableBinding =
                SwingBindings.createJTableBinding( UpdateStrategy.READ, beans, table );

        BeanProperty<MockBean, JTable> property1 = BeanProperty.create( MockBean.PROP_PROPERTY1 );
        tableBinding.addColumnBinding( property1 ).
                    setColumnName( "property1" ).
                    setEditable( true ).
                    setEditor( spinnerTableCellEditor ).
                    setRenderer( integerTableCellRenderer );

        BeanProperty<MockBean, JTable> property2 = BeanProperty.create( MockBean.PROP_PROPERTY2 );
        tableBinding.addColumnBinding( property2 ).
                    setColumnName( "property2" ).
                    setRenderer( doubleTableCellRenderer );

        BeanProperty<MockBean, JTable> property3 = BeanProperty.create( MockBean.PROP_PROPERTY3 );
        tableBinding.addColumnBinding( property3 ).
                    setColumnName( "property3" );

        final BindingGroup selectionBind = new BindingGroup();

        final JSpinner spinner = new JSpinner();
        final JTextField spinerF = ( (JSpinner.DefaultEditor) spinner.getEditor() ).getTextField();
        spinerF.setColumns( 50 );
        final JTextField txtDouble = new JTextField( 50 );
        final JTextField txtString = new JTextField( 50 );

        selectionBind.addBinding(
                Bindings.createAutoBinding(
                        UpdateStrategy.READ_WRITE,
                        table, BeanProperty.create( property( MockBean.PROP_PROPERTY1 ) ),
                        spinner, BeanProperty.create( "value" ) ) );

        selectionBind.addBinding(
                Bindings.createAutoBinding(
                        UpdateStrategy.READ_WRITE,
                        table, BeanProperty.create( property( MockBean.PROP_PROPERTY2 ) ),
                        txtDouble, BeanProperty.create( "text_ON_FOCUS_LOST" ) ) );

        selectionBind.addBinding(
                Bindings.createAutoBinding(
                        UpdateStrategy.READ_WRITE,
                        table, BeanProperty.create( property( MockBean.PROP_PROPERTY3 ) ),
                        txtString, BeanProperty.create( "text_ON_FOCUS_LOST" ) ) );

        tableBinding.bind();
        table.getSelectionModel().setSelectionInterval( 0, 0 );
        selectionBind.bind();

        Assertions.assertEquals( 3, table.getColumnCount() );

        Assertions.assertEquals( table.getModel().getValueAt( 0, 0 ), spinner.getValue() );
        Assertions.assertEquals( table.getModel().getValueAt( 0, 1 ), Double.parseDouble( txtDouble.getText() ) );
        Assertions.assertEquals( table.getModel().getValueAt( 0, 2 ), txtString.getText() );

        Assertions.assertEquals( beans.get( 0 ).getProperty1(), spinner.getValue() );
        Assertions.assertEquals( beans.get( 0 ).getProperty2(), Double.parseDouble( txtDouble.getText() ) );
        Assertions.assertEquals( beans.get( 0 ).getProperty3(), txtString.getText() );

        //        final JFrame frame = new JFrame( "Test" );
        //        JPanel contentPane = new JPanel( new BorderLayout() );
        //
        //        JPanel header = new JPanel( new GridLayout( 1, 3 ) );
        //        contentPane.add( header, BorderLayout.NORTH );
        //
        //        JPanel propPanel1 = new JPanel( new BorderLayout() );
        //        header.add( propPanel1 );
        //        propPanel1.add( new Label( "property1" ), BorderLayout.WEST );
        //        propPanel1.add( spinner, BorderLayout.CENTER );
        //
        //        JPanel propPanel2 = new JPanel( new BorderLayout() );
        //        header.add( propPanel2 );
        //        propPanel2.add( new Label( "property2" ), BorderLayout.WEST );
        //        propPanel2.add( txtDouble, BorderLayout.CENTER );
        //
        //        JPanel propPanel3 = new JPanel( new BorderLayout() );
        //        header.add( propPanel3 );
        //        propPanel3.add( new Label( "property3" ), BorderLayout.WEST );
        //        propPanel3.add( txtString, BorderLayout.CENTER );
        //
        //        JPanel center = new JPanel( new BorderLayout() );
        //        contentPane.add( center, BorderLayout.CENTER );
        //        center.add( new JScrollPane( table ), BorderLayout.CENTER );
        //
        //        frame.setContentPane( contentPane );
        //        frame.setSize( 680, 480 );
        //        frame.setVisible( true );
        //
        //        frame.invalidate();
        //        frame.revalidate();
        //
        //        Thread.sleep( 60000 );
    }

    @Test
    public void testSetRendererSetEditor() {
        final List<MockBean> beans = new ArrayList<>();
        beans.add( new MockBean( 1, 11.1, "First row" ) );
        beans.add( new MockBean( 2, 22.2, "Second row" ) );
        beans.add( new MockBean( 3, 33.3, "Third row" ) );
        beans.add( new MockBean( 4, 44.4, "Fourth row" ) );

        final IntegerTableCellRenderer integerTableCellRenderer = new IntegerTableCellRenderer();
        final DoubleTableCellRenderer doubleTableCellRenderer = new DoubleTableCellRenderer();
        final SpinnerTableCellEditor spinnerTableCellEditor = new SpinnerTableCellEditor();

        final JTable table = new JTable();
        final JTableBinding<MockBean, List<MockBean>, JTable> tableBinding =
                SwingBindings.createJTableBinding( UpdateStrategy.READ, beans, table );

        BeanProperty<MockBean, JTable> property1 = BeanProperty.create( MockBean.PROP_PROPERTY1 );
        tableBinding.addColumnBinding( property1 ).
                    setColumnName( "property1" ).
                    setEditable( true ).
                    setEditor( spinnerTableCellEditor ).
                    setRenderer( integerTableCellRenderer );

        BeanProperty<MockBean, JTable> property2 = BeanProperty.create( MockBean.PROP_PROPERTY2 );
        tableBinding.addColumnBinding( property2 ).
                    setColumnName( "property2" ).
                    setRenderer( doubleTableCellRenderer );

        BeanProperty<MockBean, JTable> property3 = BeanProperty.create( MockBean.PROP_PROPERTY3 );
        tableBinding.addColumnBinding( property3 ).
                    setColumnName( "property3" );

        tableBinding.bind();

        Assertions.assertEquals( 3, table.getColumnCount() );

        final TableColumnModel columnModel = table.getColumnModel();

        Assertions.assertSame( integerTableCellRenderer, columnModel.getColumn( 0 ).getCellRenderer() );
        Assertions.assertSame( doubleTableCellRenderer, columnModel.getColumn( 1 ).getCellRenderer() );
        Assertions.assertNull( columnModel.getColumn( 2 ).getCellRenderer() );

        Assertions.assertSame( spinnerTableCellEditor, columnModel.getColumn( 0 ).getCellEditor() );
        Assertions.assertNull( columnModel.getColumn( 1 ).getCellEditor() );
        Assertions.assertNull( columnModel.getColumn( 2 ).getCellEditor() );

        //        final JFrame frame = new JFrame("Test");
        //        final Container contentPane = frame.getContentPane();
        //        contentPane.setLayout(new BorderLayout());
        //        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
        //        contentPane.add(new JLabel("Test"), BorderLayout.NORTH);
        //        frame.setSize(680, 480);
        //        frame.setVisible(true);
        //
        //        Thread.sleep(60000);
    }
}
