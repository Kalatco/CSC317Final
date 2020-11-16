from rest_framework import serializers
from leaderboard.models import Score

class ScoreSerializer(serializers.ModelSerializer):

    class Meta:
        model = Score
        fields = '__all__'
