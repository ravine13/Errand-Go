"use client"

import { useState, useEffect } from "react"
import { Card, CardBody, CardHeader, Button, Chip, Progress, Divider } from "@nextui-org/react"
import { Plus, Clock, CheckCircle, DollarSign, Star, MapPin } from "lucide-react"
import Link from "next/link"
import { useAuth } from "@/contexts/AuthContext"

interface Task {
  id: string
  title: string
  description: string
  status: "PENDING" | "IN_PROGRESS" | "COMPLETED"
  budget: number
  category: string
  createdAt: string
}

export default function UserDashboard() {
  const { user } = useAuth()
  const [tasks, setTasks] = useState<Task[]>([])
  const [stats, setStats] = useState({
    totalTasks: 0,
    completedTasks: 0,
    totalSpent: 0,
    averageRating: 4.8,
  })

  useEffect(() => {
    // Mock data - replace with actual API calls
    setTasks([
      {
        id: "1",
        title: "Grocery Shopping",
        description: "Buy groceries from the list provided",
        status: "IN_PROGRESS",
        budget: 50,
        category: "Shopping",
        createdAt: "2024-01-15",
      },
      {
        id: "2",
        title: "House Cleaning",
        description: "Deep clean 2-bedroom apartment",
        status: "COMPLETED",
        budget: 120,
        category: "Cleaning",
        createdAt: "2024-01-10",
      },
    ])

    setStats({
      totalTasks: 15,
      completedTasks: 12,
      totalSpent: 850,
      averageRating: 4.8,
    })
  }, [])

  const getStatusColor = (status: string) => {
    switch (status) {
      case "PENDING":
        return "warning"
      case "IN_PROGRESS":
        return "primary"
      case "COMPLETED":
        return "success"
      default:
        return "default"
    }
  }

  const getStatusIcon = (status: string) => {
    switch (status) {
      case "PENDING":
        return <Clock className="w-4 h-4" />
      case "IN_PROGRESS":
        return <Clock className="w-4 h-4" />
      case "COMPLETED":
        return <CheckCircle className="w-4 h-4" />
      default:
        return <Clock className="w-4 h-4" />
    }
  }

  return (
    <div className="max-w-7xl mx-auto p-6">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Welcome back, {user?.username}!</h1>
        <p className="text-gray-600">Manage your tasks and track your progress</p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-blue-100 rounded-full">
              <Clock className="w-6 h-6 text-blue-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Total Tasks</p>
              <p className="text-2xl font-bold">{stats.totalTasks}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-green-100 rounded-full">
              <CheckCircle className="w-6 h-6 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Completed</p>
              <p className="text-2xl font-bold">{stats.completedTasks}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-purple-100 rounded-full">
              <DollarSign className="w-6 h-6 text-purple-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Total Spent</p>
              <p className="text-2xl font-bold">${stats.totalSpent}</p>
            </div>
          </CardBody>
        </Card>

        <Card>
          <CardBody className="flex flex-row items-center gap-4">
            <div className="p-3 bg-yellow-100 rounded-full">
              <Star className="w-6 h-6 text-yellow-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Avg Rating</p>
              <p className="text-2xl font-bold">{stats.averageRating}</p>
            </div>
          </CardBody>
        </Card>
      </div>

      <div className="grid lg:grid-cols-3 gap-6">
        {/* Recent Tasks */}
        <div className="lg:col-span-2">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between">
              <h2 className="text-xl font-semibold">Recent Tasks</h2>
              <Button as={Link} href="/tasks/create" color="primary" startContent={<Plus className="w-4 h-4" />}>
                New Task
              </Button>
            </CardHeader>
            <CardBody>
              <div className="space-y-4">
                {tasks.map((task) => (
                  <div key={task.id} className="border rounded-lg p-4">
                    <div className="flex items-start justify-between mb-2">
                      <h3 className="font-semibold">{task.title}</h3>
                      <Chip
                        color={getStatusColor(task.status)}
                        variant="flat"
                        startContent={getStatusIcon(task.status)}
                        size="sm"
                      >
                        {task.status.replace("_", " ")}
                      </Chip>
                    </div>
                    <p className="text-gray-600 text-sm mb-2">{task.description}</p>
                    <div className="flex items-center justify-between text-sm">
                      <span className="text-gray-500">{task.category}</span>
                      <span className="font-semibold">${task.budget}</span>
                    </div>
                  </div>
                ))}
              </div>
            </CardBody>
          </Card>
        </div>

        {/* Quick Actions */}
        <div className="space-y-6">
          <Card>
            <CardHeader>
              <h2 className="text-xl font-semibold">Quick Actions</h2>
            </CardHeader>
            <CardBody className="space-y-3">
              <Button
                as={Link}
                href="/tasks/create"
                color="primary"
                variant="flat"
                className="w-full justify-start"
                startContent={<Plus className="w-4 h-4" />}
              >
                Post New Task
              </Button>
              <Button
                as={Link}
                href="/errand-boys"
                variant="flat"
                className="w-full justify-start"
                startContent={<MapPin className="w-4 h-4" />}
              >
                Find Errand Boys
              </Button>
              <Button
                as={Link}
                href="/tasks"
                variant="flat"
                className="w-full justify-start"
                startContent={<Clock className="w-4 h-4" />}
              >
                View All Tasks
              </Button>
            </CardBody>
          </Card>

          {/* Progress */}
          <Card>
            <CardHeader>
              <h2 className="text-xl font-semibold">This Month</h2>
            </CardHeader>
            <CardBody>
              <div className="space-y-4">
                <div>
                  <div className="flex justify-between text-sm mb-1">
                    <span>Tasks Completed</span>
                    <span>
                      {stats.completedTasks}/{stats.totalTasks}
                    </span>
                  </div>
                  <Progress value={(stats.completedTasks / stats.totalTasks) * 100} color="success" size="sm" />
                </div>
                <Divider />
                <div className="text-center">
                  <p className="text-2xl font-bold text-primary">${stats.totalSpent}</p>
                  <p className="text-sm text-gray-600">Total Spent</p>
                </div>
              </div>
            </CardBody>
          </Card>
        </div>
      </div>
    </div>
  )
}
