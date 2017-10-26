package com.example.as;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import com.netflix.hystrix.metric.consumer.HystrixDashboardStream;
import com.netflix.hystrix.serial.SerialHystrixDashboardData;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 
 * @author AXG8965
 *
 */
@Service
public class Hystrixdashboard{
   
	private  Observable<String> getHystrixProperties() {
    	Observable<String> sampleStreamObj = null;
    	sampleStreamObj = HystrixDashboardStream.getInstance().observe().concatMap(new Func1<HystrixDashboardStream.DashboardData, Observable<String>>() {
            @Override
            public Observable<String> call(HystrixDashboardStream.DashboardData dashboardData) {
                return Observable.from(SerialHystrixDashboardData.toMultipleJsonStrings(dashboardData));
            }
        });
    	return sampleStreamObj;
    }
    /**
     * get Dash board data.
     * 
     * @param list
     */
	public void getDashBoardData(List<String> list,int count) throws Exception{
    	Subscription sampleSubscription = null;
    	final AtomicBoolean moreDataWillBeSent = new AtomicBoolean(true);
	    final AtomicInteger countLoop = new AtomicInteger(0);
     	try {
      		sampleSubscription = getHystrixProperties()
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("HystrixSampleSseServlet: ({}) received unexpected OnCompleted from sample stream : "+ getClass().getSimpleName());
                        moreDataWillBeSent.set(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        moreDataWillBeSent.set(false);
                    }

                    @Override
                    public void onNext(String sampleDataAsString) {
                        if (sampleDataAsString != null) {
                            try {
                            	list.add(sampleDataAsString);
                               } catch (Exception ex) {
                            	  moreDataWillBeSent.set(false);
                            }
                        }
                    }
                });
	        while (moreDataWillBeSent.get()) {
                try {
                      Thread.sleep(500);
                       if(countLoop.getAndIncrement()>count) {
                    	   moreDataWillBeSent.set(false);
                       }
                } catch (Exception ex) {
                    moreDataWillBeSent.set(false);
                }
            }
    	}finally {
    		if (sampleSubscription != null && !sampleSubscription.isUnsubscribed()) {
                sampleSubscription.unsubscribe();
            }
    	}
    }
			    
}
