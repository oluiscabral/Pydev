// Autogenerated AST node
package org.python.pydev.parser.jython.ast;

import org.python.pydev.parser.jython.SimpleNode;
import java.util.Arrays;
import org.python.pydev.parser.jython.SpecialStr;

public final class Slice extends sliceType {
    public exprType lower;
    public exprType upper;
    public exprType step;

    public Slice(exprType lower, exprType upper, exprType step) {
        this.lower = lower;
        this.upper = upper;
        this.step = step;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lower == null) ? 0 : lower.hashCode());
        result = prime * result + ((upper == null) ? 0 : upper.hashCode());
        result = prime * result + ((step == null) ? 0 : step.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Slice other = (Slice) obj;
        if (lower == null) { if (other.lower != null) return false;}
        else if (!lower.equals(other.lower)) return false;
        if (upper == null) { if (other.upper != null) return false;}
        else if (!upper.equals(other.upper)) return false;
        if (step == null) { if (other.step != null) return false;}
        else if (!step.equals(other.step)) return false;
        return true;
    }
    @Override
    public Slice createCopy() {
        return createCopy(true);
    }
    @Override
    public Slice createCopy(boolean copyComments) {
        Slice temp = new Slice(lower!=null?(exprType)lower.createCopy(copyComments):null,
        upper!=null?(exprType)upper.createCopy(copyComments):null,
        step!=null?(exprType)step.createCopy(copyComments):null);
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
        StringBuffer sb = new StringBuffer("Slice[");
        sb.append("lower=");
        sb.append(dumpThis(this.lower));
        sb.append(", ");
        sb.append("upper=");
        sb.append(dumpThis(this.upper));
        sb.append(", ");
        sb.append("step=");
        sb.append(dumpThis(this.step));
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Object accept(VisitorIF visitor) throws Exception {
        return visitor.visitSlice(this);
    }

    @Override
    public void traverse(VisitorIF visitor) throws Exception {
        if (lower != null) {
            lower.accept(visitor);
        }
        if (upper != null) {
            upper.accept(visitor);
        }
        if (step != null) {
            step.accept(visitor);
        }
    }

}
