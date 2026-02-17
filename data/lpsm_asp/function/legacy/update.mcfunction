# Made by: @Legopitstop
# Desc: Updates the pose of the armor stand using the pose ID stored on the marker
#
# Called By: lpsm_asp:armor_stand/next

# Fail if @s is not the data marker
execute unless entity @s[type=marker, tag=ArmorStandData] run return 0
execute unless data storage lpsm_asp:poses pose run return 0

# Get pose data from ID
# data modify storage lpsm_asp:poses find set from entity @s data.id
# function lpsm_asp:armor_stand/find_id

# Apply
execute as @e[type=armor_stand, sort=nearest, limit=1] run data modify entity @s Pose set from storage lpsm_asp:poses pose.Pose
