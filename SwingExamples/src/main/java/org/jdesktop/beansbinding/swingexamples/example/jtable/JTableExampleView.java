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
 * $Id: JTableExampleView.java 128 2009-07-09 13:34:05Z fabriziogiudici $
 *
 **********************************************************************************************************************/
package org.jdesktop.beansbinding.swingexamples.example.jtable;

import java.util.ArrayList;
import javax.swing.JPanel;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.examples.beans.Person;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

/***********************************************************************************************************************
 *
 * @author Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class JTableExampleView extends JPanel {

    public JTableExampleView() {
        initComponents();

        final ObservableList<Person> list =
                ObservableCollections.observableListHelper(new ArrayList<Person>()).getObservableList();
        list.add(new Person("Jane", "Doe", "98765"));
        list.add(new Person("Bob" ,"Smith", "91234"));

        final JTableBinding tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, list, taTable);

        tableBinding.addColumnBinding(ELProperty.create("${firstName}")).
            setColumnName("First name").
            setColumnClass(String.class);

        tableBinding.addColumnBinding(ELProperty.create("${lastName}")).
            setColumnName("Last name").
            setColumnClass(String.class);

        tableBinding.addColumnBinding(ELProperty.create("${zip}")).
            setColumnName("Zip").
            setColumnClass(String.class);

        final BindingGroup bindingGroup = new BindingGroup();
        bindingGroup.addBinding(tableBinding);
        tableBinding.bind();

        bindingGroup.addBinding(Bindings.createAutoBinding(
                AutoBinding.UpdateStrategy.READ_WRITE,
                taTable, ELProperty.create("${selectedElement.firstName}"),
                tfFirstName, BeanProperty.create("text")));

        bindingGroup.addBinding(Bindings.createAutoBinding(
                AutoBinding.UpdateStrategy.READ_WRITE,
                taTable, ELProperty.create("${selectedElement.lastName}"),
                tfLastName, BeanProperty.create("text")));

        bindingGroup.addBinding(Bindings.createAutoBinding(
                AutoBinding.UpdateStrategy.READ_WRITE,
                taTable, ELProperty.create("${selectedElement.zip}"),
                tfZip, BeanProperty.create("text")));

        bindingGroup.bind();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btAdd = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        btClear = new javax.swing.JButton();
        btReset = new javax.swing.JButton();
        spScrollPane = new javax.swing.JScrollPane();
        taTable = new javax.swing.JTable();
        tfFirstName = new javax.swing.JTextField();
        tfZip = new javax.swing.JTextField();
        lbFirstName = new javax.swing.JLabel();
        lbLastName = new javax.swing.JLabel();
        lbZip = new javax.swing.JLabel();
        tfLastName = new javax.swing.JTextField();

        setName("Form"); // NOI18N

        btAdd.setName("btAdd"); // NOI18N

        btDelete.setName("btDelete"); // NOI18N

        btClear.setName("btClear"); // NOI18N

        btReset.setName("btReset"); // NOI18N

        spScrollPane.setName("spScrollPane"); // NOI18N

        taTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        taTable.setName("taTable"); // NOI18N
        spScrollPane.setViewportView(taTable);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.jdesktop.beansbinding.swingexamples.SwingExamplesApplication.class).getContext().getResourceMap(JTableExampleView.class);
        tfFirstName.setText(resourceMap.getString("tfFirstName.text")); // NOI18N
        tfFirstName.setName("tfFirstName"); // NOI18N

        tfZip.setText(resourceMap.getString("tfZip.text")); // NOI18N
        tfZip.setName("tfZip"); // NOI18N

        lbFirstName.setText(resourceMap.getString("lbFirstName.text")); // NOI18N
        lbFirstName.setName("lbFirstName"); // NOI18N

        lbLastName.setText(resourceMap.getString("lbLastName.text")); // NOI18N
        lbLastName.setName("lbLastName"); // NOI18N

        lbZip.setText(resourceMap.getString("lbZip.text")); // NOI18N
        lbZip.setName("lbZip"); // NOI18N

        tfLastName.setText(resourceMap.getString("tfLastName.text")); // NOI18N
        tfLastName.setName("tfLastName"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(lbLastName)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tfLastName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lbFirstName)
                            .add(lbZip))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(tfZip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(tfFirstName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, spScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btDelete)
                    .add(btClear)
                    .add(btReset)
                    .add(btAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {btAdd, btClear, btDelete, btReset}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[] {lbFirstName, lbLastName, lbZip}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(btAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btDelete)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btClear)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btReset))
                    .add(spScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lbFirstName)
                    .add(tfFirstName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lbLastName)
                    .add(tfLastName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(tfZip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lbZip))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btClear;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btReset;
    private javax.swing.JLabel lbFirstName;
    private javax.swing.JLabel lbLastName;
    private javax.swing.JLabel lbZip;
    private javax.swing.JScrollPane spScrollPane;
    private javax.swing.JTable taTable;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    private javax.swing.JTextField tfZip;
    // End of variables declaration//GEN-END:variables
}
