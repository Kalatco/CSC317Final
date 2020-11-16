from django.shortcuts import render
from rest_framework import viewsets
from leaderboard.serializers import ScoreSerializer
from leaderboard.models import Score

class ScoreViewSet(viewsets.ModelViewSet):
    top_scores = Score.objects.order_by('-score').values_list('score', flat=True).distinct()

    serializer_class = ScoreSerializer
    queryset = Score.objects.order_by('-score').filter(score__in=top_scores[:100])
