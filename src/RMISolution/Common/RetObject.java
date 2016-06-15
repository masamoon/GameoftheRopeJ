package RMISolution.Common;

import java.io.Serializable;

public class RetObject implements Serializable{

    private VectorClock vc;

    private Object retValue1, retValue2;

    public RetObject (VectorClock vc, Object ret){
        this.vc = vc;
        this.retValue1 = ret;
    }
    public RetObject (VectorClock vc, Object ret, Object state){
        this.vc = vc;
        this.retValue1 = ret;
        this.retValue2 = state;
    }
    public synchronized VectorClock getVectorClock (){
        return this.vc;
    }
    public synchronized Object getRetvalue1 (){
        return this.retValue1;
    }
    public synchronized Object getRetValue2(){
        return this.retValue2;
    }
}
