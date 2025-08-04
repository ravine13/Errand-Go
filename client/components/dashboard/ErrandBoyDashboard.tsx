"use client"

import { useState, useEffect } from "react"
import { Card, CardBody, CardHeader, Button, Chip, Progress, Divider } from "@nextui-org/react"
import { Search, Clock, CheckCircle, DollarSign, Star, TrendingUp } from "lucide-react"
import Link from "next/link"
import { useAuth } from "@/contexts/AuthContext"

interface Task {
  id: string
  title: string
  description: string
  budget: number
  category: string
  location: string
  postedBy: string
  urgency: "LOW" | "MEDIUM" | "HIGH"
}

export default function ErrandBoyDashboard() {
  const { user } = useAuth()
  const [availableTasks, setAvailableTasks] = useState<Task[]>([])
  const [stats, setStats] = useState({
    tasksCompleted: 0,
    totalEarnings: 0,
    averageRating: 4.9,
    successRate: 98,
  })

  useEffect(() => {
    // Mock data - replace with actual API calls
    setAvailableTasks([
      {
        id: "1",
        title: "Grocery Shopping at Walmart",
        description: "Need someone to buy groceries from the provided list",
        budget: 45,
        category: "Shopping",
        location: "Downtown",
        postedBy: "Sarah M.",
        urgency: "MEDIUM",
      },
      {
        id: "2",
        title: "Package Delivery",
        description: "Pick up package from UPS store and deliver to address",
        budget: 25,
        category: "Delivery",
        location: "Midtown",
        postedBy: "John D.",
        urgency: "HIGH",
      },
    ])

    setStats({
      tasksCompleted: 47,
      totalEarnings: 1250,
      averageRating: 4.9,
      successRate: 98,
    })
  }, [])

  const getUrgencyColor = (urgency: string) => {
    switch (urgency) {
      case "LOW":
        return "success"
      case "MEDIUM":
        return "warning"
      case "HIGH":
        return "danger"
      default:
        return "default"
    }
  }

  return (
    <div className="max-w-7xl mx-auto p-6">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Hello, {user?.username}!</h1>
        <p className="text-gray-600">Find new tasks and grow your earnings</p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-green-100 rounded-full">
              <CheckCircle className="w-6 h-6 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Tasks Completed</p>
              <p className="text-2xl font-bold">{stats.tasksCompleted}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-blue-100 rounded-full">
              <DollarSign className="w-6 h-6 text-blue-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Total Earnings</p>
              <p className="text-2xl font-bold">${stats.totalEarnings}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-yellow-100 rounded-full">
              <Star className="w-6 h-6 text-yellow-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Rating</p>
              <p className="text-2xl font-bold">{stats.averageRating}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-purple-100 rounded-full">
              <TrendingUp className="w-6 h-6 text-purple-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Success Rate</p>
              <p className="text-2xl font-bold">{stats.successRate}%</p>
            </div>
          </CardBody>
        </Card>
      </div>

      <div className="grid lg:grid-cols-3 gap-6">
        {/* Available Tasks */}
        <div className="lg:col-span-2">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between">
              <h2 className="text-xl font-semibold">Available Tasks</h2>
              <Button as={Link} href="/tasks" color="primary" startContent={<Search className="w-4 h-4" />}>
                Browse All
              </Button>
            </CardHeader>
            <CardBody>
              <div className="space-y-4">
                {availableTasks.map((task) => (
                  <div key={task.id} className="border rounded-lg p-4 hover:shadow-md transition-shadow">
                    <div className="flex items-start justify-between mb-2">
                      <h3 className="font-semibold">{task.title}</h3>
                      <Chip color={getUrgencyColor(task.urgency)} variant="flat" size="sm">
                        {task.urgency}
                      </Chip>
                    </div>
                    <p className="text-gray-600 text-sm mb-3">{task.description}</p>
                    <div className="flex items-center justify-between mb-3">
                      <div className="flex items-center gap-4 text-sm text-gray-500">
                        <span>{task.category}</span>
                        <span>{task.location}</span>
                        <span>by {task.postedBy}</span>
                      </div>
                      <span className="font-bold text-lg text-green-600">${task.budget}</span>
                    </div>
                    <Button color="primary" size="sm" className="w-full">
                      Apply for Task
                    </Button>
                  </div>
                ))}
              </div>
            </CardBody>
          </Card>
        </div>

        {/* Sidebar */}
        <div className="space-y-6">
          {/* Quick Actions */}
          <Card>
            <CardHeader>
              <h2 className="text-xl font-semibold">Quick Actions</h2>
            </CardHeader>
            <CardBody className="space-y-3">
              <Button
                as={Link}
                href="/tasks"
                color="primary"
                variant="flat"
                className="w-full justify-start"
                startContent={<Search className="w-4 h-4" />}
              >
                Find Tasks
              </Button>
              <Button
                as={Link}
                href="/my-tasks"
                variant="flat"
                className="w-full justify-start"
                startContent={<Clock className="w-4 h-4" />}
              >
                My Active Tasks
              </Button>
              <Button
                as={Link}
                href="/earnings"
                variant="flat"
                className="w-full justify-start"
                startContent={<DollarSign className="w-4 h-4" />}
              >
                View Earnings
              </Button>
            </CardBody>
          </Card>

          {/* Performance */}
          <Card>
            <CardHeader>
              <h2 className="text-xl font-semibold">Performance</h2>
            </CardHeader>
            <CardBody>
              <div className="space-y-4">
                <div>
                  <div className="flex justify-between text-sm mb-1">
                    <span>Success Rate</span>
                    <span>{stats.successRate}%</span>
                  </div>
                  <Progress value={stats.successRate} color="success" size="sm" />
                </div>
                <div>
                  <div className="flex justify-between text-sm mb-1">
                    <span>Customer Rating</span>
                    <span>{stats.averageRating}/5.0</span>
                  </div>
                  <Progress value={(stats.averageRating / 5) * 100} color="warning" size="sm" />
                </div>
                <Divider />
                <div className="text-center">
                  <p className="text-2xl font-bold text-primary">${stats.totalEarnings}</p>
                  <p className="text-sm text-gray-600">Total Earnings</p>
                </div>
              </div>
            </CardBody>
          </Card>
        </div>
      </div>
    </div>
  )
}
