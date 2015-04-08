class MyRunnable
   def run
      while(@keepRun)
         if (@totalProgressTime >= 100) 
            @progress.setProgress(100)
         else
            @totalProgressTime = @totalProgressTime + 5;
            Thread.sleep(80)
         end       
                            
                        #} catch (InterruptedException e) {
                         #   e.printStackTrace();
                        #}

        @progressBarHandler.post(MyRunnable1)
      end
   end
end

class MyRunnable1
   def run
      @progress.setProgress(@totalProgressTime)
   end
end

#################################################################

class MainActivity < Android::App::Activity
 
   

  def onCreate(savedInstanceState)
    super
    
    @totalProgressTime = 100
    @keeprun = true
    @progressBarbHandler = Android::Os::Handler.new


    setContentView(resources.getIdentifier('activity_main', 'layout', 'com.yourcompany.battlescreenrm'))

   

    @progress = findViewById(resources.getIdentifier('progressBar1', 'id', 'com.yourcompany.battlescreenrm'))
    @progress.setProgress(@totalProgressTime)

    @rl =  findViewById(resources.getIdentifier('progressBar1', 'id', 'com.yourcompany.battlescreenrm'))
    @tv = findViewById(resources.getIdentifier('myTextView', 'id', 'com.yourcompany.battlescreenrm'))

    
    

    @moveLefttoRight = Android::View::Animation::TranslateAnimation.new(100, 200, 0, 0)
        @moveLefttoRight.setDuration(30)
        @moveLefttoRight.setRepeatCount(1);
        @moveLefttoRight.setRepeatMode(2);
        @moveLefttoRight.setFillAfter(false);
     
     @progress.setOnClickListener (self)    
    
  end


#@t = Java::Lang::Thread.new(MyRunnable)
#@t.start

  


# def onClick(view)
#     if(@totalProgressTime==100)
#        @totalProgressTime = 0      
#        @progress.setProgress(@totalProgressTime)
#        
#         @tv.startAnimation(@moveLefttoRight)
#        while @totalProgressTime <= 100 do
#           @totalProgressTime = @totalProgressTime + 1
#           @progress.setProgress(@totalProgressTime)
#        end
#     end  
# end


def onClick(view)
        @totalProgressTime = 0      
        @progress.setProgress(@totalProgressTime)
        @tv.startAnimation(@moveLefttoRight)
end




#def onDestroy
 #   @keepRun = false
     
  #  super
 # end




end




