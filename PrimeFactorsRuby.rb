require 'prime'

def PrimeFactors(n)
Prime.prime_division(n).flat_map { |a , b| [a]*b }
end

puts "Enter a number"
number = gets.chomp
print PrimeFactors(Integer(number))
print "\n"
