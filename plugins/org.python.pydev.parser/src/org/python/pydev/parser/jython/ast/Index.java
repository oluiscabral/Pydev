// Autogenerated AST node
package org.python.pydev.parser.jython.ast;

import org.python.pydev.parser.jython.SimpleNode;
import java.util.Arrays;
import org.python.pydev.parser.jython.SpecialStr;

public final class Index extends sliceType {
    public exprType value;

    public Index(exprType value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Index other = (Index) obj;
        if (value == null) { if (other.value != null) return false;}
        else if (!value.equals(other.value)) return false;
        return true;
    }
    @Override
    public Index createCopy() {
        return createCopy(true);
    }
    @Override
    public Index createCopy(boolean copyComments) {
        Index temp = new Index(value!=null?(exprType)value.createCopy(copyComments):null);
        temp.beginLine = this.beginLine;
        temp.beginColumn = this.beginColumn;
        if(this.specialsBefore != null && copyComments){
            for(Object o:this.specialsBefore){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsBefore().add(commentType.createCopy(copyComments));
                }
            }
        }
        if(this.specialsAfter != null && copyComments){
            for(Object o:this.specialsAfter){
                if(o instanceof commentType){
                    commentType commentType = (commentType) o;
                    temp.getSpecialsAfter().add(commentType.createCopy(copyComments));
                } else if (o instanceof SpecialStr) {
                    SpecialStr specialStr = (SpecialStr) o;
                    Name name = new Name(specialStr.str, -1, false);
                    name.beginColumn = specialStr.getBeginCol();
                    name.beginLine = specialStr.getBeginLine();
                    temp.getSpecialsAfter().add(name);
                }
            }
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Index[");
        sb.append("value=");
        sb.append(dumpThis(this.value));
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitIndex(this);
    }

    @Override
    public void traverse(VisitorIF visitor) throws Exception {
        if (value != null) {
            value.accept(visitor);
        }
    }

}
