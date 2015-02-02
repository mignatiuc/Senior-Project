#################################################################################
#Mars Rover Kata Ruby
#################################################################################

#initialize
obsFound=false #no obstacles encountered by default
msg="Clear" #status message is "Clear" by default
obstacles = [[0,5][0,10]] #set up 2 obstacles
maxLimPointLocation=[24,24] # let the grid be of size 25 x 25
directionEnum = 0 #Heading North by defualt

#starting from (0,0) by default
x = 0
y = 0
#Instruction String
instruct=""


# clockwise increment
def readInstruction(directionEnum)
    case directionEnum
        when 0
        "N"
        when 1
        "E"
        when 2
        "S"
        when 3
        "W"
    end
end

#################################################################################

#Step Forward
def moveF(directionEnum, xCorr, yCorr, obstacles)
    obsFound=false #Resets Status to "Clear" every time
    case directionEnum
        when 0
        yCorr+=1
        when 1
        xCorr+=1
        when 2
        yCorr-=1
        when 3
        xCorr-=1
    end
    
    if obstacles.find { |a,b| a == xCorr && b == yCorr }
        obsFound = true
        case directionEnum
            when 0
            yCorr-=1
            when 1
            xCorr-=1
            when 2
            yCorr+=1
            when 3
            xCorr+=1

        end
    end
    return xCorr, yCorr, obsFound
end



#Step Backward (Face the same direction but move in the opposite one)
def moveB(directionEnum, xCorr, yCorr, obstacles)
    obsFound = false #Resets Status to "Clear" every time
    case directionEnum
        when 0
        yCorr-=1
        when 1
        xCorr-=1
        when 2
        yCorr+=1
        when 3
        xCorr+=1
    end
    
    if obstacles.find { |a,b| a == xCorr && b == yCorr }
        obsFound = true
        case directionEnum
            when 0
            yCorr+=1
            when 1
            xCorr+=1
            when 2
            yCorr-=1
            when 3
            xCorr-=1

        end
    end
    return xCorr, yCorr, obsFound
end

#################################################################################

#turn right 90 degrees
def RotateCompassR(directionEnum)
    if directionEnum < 3
        directionEnum+=1
        else
        directionEnum = 0
    end
    return directionEnum, false # "false" Resets Status to "Clear" each iteration
end

#turn left 90 degrees
def RotateCompassL(directionEnum)
    if directionEnum > 0
        directionEnum-=1
        else
        directionEnum = 3
    end
    
    return directionEnum, false # #Resets Status to "Clear" each iteration
end



#################################################################################
while (instruct != "e")

    if(obsFound == false)
        msg = "Clear"
    else
        msg = "Obstacle Found"
    end
    
    
    print "x:#{x} y:#{y} Compass Shows:#{readInstruction(directionEnum)} Status:#{msg} \n"
    
    
    readInstructionStr = gets.chomp
    

    readInstructionStr.each_char do |instruct|
        
        case instruct
            when "f"
            x, y, obsFound = moveF(directionEnum, x, y, obstacles)
            when "r"
            directionEnum, obsFound = RotateCompassR(directionEnum)
            when "b"
            x, y, obsFound = moveB(directionEnum, x, y, obstacles)
            when "l"
            directionEnum, obsFound = RotateCompassL(directionEnum)
            when "e"
            exit
        end

        #Wrap around the map
        if x > maxLimPointLocation[0]
            x-=(maxLimPointLocation[0])
            x-=1
        end
        if y > maxLimPointLocation[1]
            y-=(maxLimPointLocation[1])
            y-=1
        end
    
        if x < 0
            x+=maxLimPointLocation[0]
            x+=1
        end
        if y < 0
            y+=(maxLimPointLocation[1])
            y+=1
        end
    end
end
