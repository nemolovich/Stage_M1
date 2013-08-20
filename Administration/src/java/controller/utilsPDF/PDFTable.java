/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utilsPDF;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brian GOHIER
 */
public class PDFTable extends PdfPTable
{
    private List<PdfPCell> cellList=new ArrayList<PdfPCell>();
    private float borderWidthTop=0f;
    private float bottomWidthBottom=0f;
    private float borderWidthLeft=0f;
    private float borderWidthRight=0f;
    private int lines=0;

    public PDFTable(int numColumns)
    {
        super(numColumns);
    }
    
    public void add()
    {
        this.add(Element.ALIGN_CENTER);
    }
    
    public void add(int align)
    {
        PdfPCell cell=new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(align);
        super.addCell(cell);
        this.cellList.add(cell);
    }
    
    public void add(Element e)
    {
        this.add(e,Element.ALIGN_CENTER);
    }
    
    public void add(Element e, int align)
    {
        this.addCol(e, align, 1);
    }
    
    public void addCol(Element e, int columns)
    {
        this.addCol(e, Element.ALIGN_CENTER, columns);
    }
    
    public void addCol(Element e, int align, int columns)
    {
        PdfPCell cell = null;
        if(e instanceof Image)
        {
            cell=new PdfPCell((Image)e);
        }
        else if(e instanceof Phrase)
        {
            cell=new PdfPCell((Phrase)e);
        }
        else if(e instanceof PdfPTable)
        {
            cell=new PdfPCell((PdfPTable)e);
        }
        else if(e instanceof PdfPCell)
        {
            cell=new PdfPCell((PdfPCell)e);
        }
        if(cell==null)
        {
            return;
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(align);
        cell.setBorderWidthTop(this.borderWidthTop);
        cell.setBorderWidthBottom(this.bottomWidthBottom);
        cell.setBorderWidthLeft(this.borderWidthLeft);
        cell.setBorderWidthRight(this.borderWidthRight);
        cell.setColspan(columns);
        super.addCell(cell);
        this.cellList.add(cell);
        this.lines++;
    }

    public void setHeader(String[][] tableHeader)
    {
        Font headerFont = 
            FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
        if(tableHeader.length<this.getNumberOfColumns())
        {
            throw new IndexOutOfBoundsException("Le nombre de titres est trop court");
        }
        for(String[] title:tableHeader)
        {
            if(title.length<2)
            {
                throw new IndexOutOfBoundsException("Le paramètre de titre est incorrect");
            }
            Paragraph cell=new Paragraph(title[0],headerFont);
            cell.setSpacingAfter(100);
            this.add(cell, Integer.valueOf(title[1]));
        }
        this.setHeaderRows(1);
    }
    
    public void setFooter(String[][] listFooter)
    {
        Font footerFont = 
            FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
        if(listFooter.length==0)
        {
                throw new IndexOutOfBoundsException("Nombre de paramètres pour footer incorrect");
        }
        Paragraph cell=new Paragraph(listFooter[0][0],footerFont);
        cell.setSpacingBefore(100);
        this.addCol(cell,
                Integer.valueOf(listFooter[0][1]),
                this.getNumberOfColumns()-listFooter.length+1);
        for(int i=1;i<listFooter.length;i++)
        {
            cell=new Paragraph(listFooter[i][0],footerFont);
            cell.setSpacingBefore(100);
            this.add(cell,
                Integer.valueOf(listFooter[i][1]));
        }
    }
    
    public PdfPCell getCell(int i, int j)
    {
        return this.cellList.get(((i-1)*this.getNumberOfColumns()+(j-1)));
    }
    
    public PdfPCell getCell(int i)
    {
        return this.cellList.get(i);
    }

    public List<PdfPCell> getCellList() {
        return cellList;
    }

    public void setCellList(List<PdfPCell> cellList) {
        this.cellList = cellList;
    }

    public float getBorderWidthTop() {
        return borderWidthTop;
    }

    public void setBorderWidthTop(float borderWidthTop) {
        this.borderWidthTop = borderWidthTop;
    }

    public float getBottomWidthBottom() {
        return bottomWidthBottom;
    }

    public void setBottomWidthBottom(float bottomWidthBottom) {
        this.bottomWidthBottom = bottomWidthBottom;
    }

    public float getBorderWidthLeft() {
        return borderWidthLeft;
    }

    public void setBorderWidthLeft(float borderWidthLeft) {
        this.borderWidthLeft = borderWidthLeft;
    }

    public float getBorderWidthRight() {
        return borderWidthRight;
    }

    public void setBorderWidthRight(float borderWidthRight) {
        this.borderWidthRight = borderWidthRight;
    }
}
