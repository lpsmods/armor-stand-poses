# Made by: @Legopitstop
# Desc: Runs when the datapack gets loaded
#
# Called By: #minecraft:load

# scoreboard(s)
scoreboard objectives add lpsm_asp.util dummy
scoreboard objectives add lpsm_asp.lastPower dummy
scoreboard objectives add lpsm_asp.power dummy

# storage(s)
execute unless data storage lpsm_asp:poses format_version run data modify storage lpsm_asp:poses format_version set value 1

# Transfer old storage to new.
execute if data storage poses:poses poses run data modify storage lpsm_asp:poses poses set from storage poses:poses poses
execute if data storage poses:poses poses run data remove storage poses:poses poses

# Add defaults
execute unless data storage lpsm_asp:poses poses run function #lpsm_asp:register_poses

# Run data fixers
function #lpsm_asp:datafixer
