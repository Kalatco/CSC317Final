from django.db import models

class Score(models.Model):
    username = models.CharField(max_length=50, unique=False)
    score = models.IntegerField(unique=False)
